package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.shopping_list;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.*;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.aliment.AlimentRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.unit.UnitRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.user.UserRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.shopping_list.ShoppingListMapper;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IShoppingListRepository;
import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import io.github.oliviercap.chefduplacard.domain.shopping_list.ShoppingList;
import io.github.oliviercap.chefduplacard.domain.shopping_list.ShoppingListLine;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ShoppingListRepository implements IShoppingListRepository {

    private final IShoppingListJpaRepository shoppingListJpaRepository;
    private final ShoppingListMapper shoppingListMapper;
    private final UserRepository userRepository;
    private final AlimentRepository alimentRepository;
    private final UnitRepository unitRepository;

    public ShoppingListRepository(
            IShoppingListJpaRepository shoppingListJpaRepository,
            ShoppingListMapper shoppingListMapper,
            UserRepository userRepository,
            AlimentRepository alimentRepository,
            UnitRepository unitRepository
    ) {
        this.shoppingListJpaRepository = shoppingListJpaRepository;
        this.shoppingListMapper = shoppingListMapper;
        this.userRepository = userRepository;
        this.alimentRepository = alimentRepository;
        this.unitRepository = unitRepository;
    }


    @Transactional
    @Override
    public ShoppingList createNewFromShoppingListLines(Long userId, List<ShoppingListLine> shoppingListLines) {

        UserJpa userJpa = userRepository.findUserJpa(userId);

        ShoppingListJpa newShoppingListJpa = new ShoppingListJpa(
                userJpa, new ArrayList<>(), LocalDate.now()
        );

        for (ShoppingListLine shoppingListLine : shoppingListLines) {

            //Création de l'entité jpa shopping list line
            AlimentJpa alimentJpa = alimentRepository.findAlimentJpaById(shoppingListLine.getAliment().getId().id())
                    .orElseThrow(() -> new DomainException("aliment not found"));

            UnitJpa unitJpa = unitRepository.findUnitJpaById(shoppingListLine.getUnit().getId().id())
                    .orElseThrow(() -> new DomainException("unit not found"));

            ShoppingListLineJpa lineJpa = new ShoppingListLineJpa(
                alimentJpa, unitJpa, shoppingListLine.getQuantity()
            );

            //Ajout de la shopping list line à la shopping list en sauvegarde
            newShoppingListJpa.addShoppingListLine(lineJpa);
        }

        ShoppingListJpa saved = shoppingListJpaRepository.save(newShoppingListJpa);

        return shoppingListMapper.toDomain(saved);
    }

    @Override
    public Optional<ShoppingList> findById(Long shoppingListId) {
        return shoppingListJpaRepository.findCompleteById(shoppingListId)
                .map(shoppingListMapper::toDomain);
    }
}
