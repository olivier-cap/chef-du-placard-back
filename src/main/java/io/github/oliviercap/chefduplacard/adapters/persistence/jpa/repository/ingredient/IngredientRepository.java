package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.ingredient;

import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.ingredient.IngredientMapper;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IIngredientRepository;
import io.github.oliviercap.chefduplacard.domain.food.Ingredient;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public class IngredientRepository implements IIngredientRepository {
    private final IIngredientJpaRepository ingredientJpaRepository;
    private final IngredientMapper ingredientMapper;


    public IngredientRepository(IIngredientJpaRepository ingredientJpaRepository,
                                IngredientMapper ingredientMapper
                                ) {
        this.ingredientJpaRepository = ingredientJpaRepository;
        this.ingredientMapper = ingredientMapper;
    }

    @Override
    public Optional<Ingredient> findCompleteById(Long id) {
        return ingredientJpaRepository.findCompleteById(id)
                .map(ingredientMapper::toDomain);
    }
}
