package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.menu;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.MenuJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.MenuLineJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.RecipeJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.menu.MenuMapper;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IMenuRepository;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IRecipeRepository;
import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import io.github.oliviercap.chefduplacard.domain.menu.Menu;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class MenuRepository implements IMenuRepository {

    private final IMenuJpaRepository menuJpaRepository;
    private final IRecipeRepository recipeRepository;
    private final MenuMapper menuMapper;

    public MenuRepository(IMenuJpaRepository menuJpaRepository, IRecipeRepository recipeRepository,
                          MenuMapper menuMapper) {
        this.menuJpaRepository = menuJpaRepository;
        this.recipeRepository = recipeRepository;
        this.menuMapper = menuMapper;
    }

    @Transactional
    @Override
    public Optional<Menu> findById(Long menuId) {

        //Première requête, UN SEUL fetch d'une liste: liste des recettes du menu
        Optional<MenuJpa> menuJpaOptional = menuJpaRepository.findMenuDetailsById(menuId);

        //Deuxième requête: chargement fetch de la 2e liste : liste des ingrédients de la recette
        //hibernate ne gère pas le fecth de 2 listes (qu'il voit comme deux "bags") en même temps

        if(menuJpaOptional.isEmpty()) {
            return Optional.empty();
        }

        MenuJpa menuJpa = menuJpaOptional.get();

        //Création de la liste des ids des recettes
        Set<Long> recipeIds = menuJpa
                .getMenuLineJpaList()
                .stream()
                .map(MenuLineJpa::getRecipeJpa)
                .map(RecipeJpa::getId)
                .collect(Collectors.toSet());


        //Requette des recipeJpa présentes dans le menu
        //RecipeJpa complètes (avec ingredient, aliment et unit)
        for(Long id:recipeIds) {
            recipeRepository.findJpaById(id);
        }

        return menuJpaOptional.map(menuMapper::toDomain);
    }

    @Override
    @Transactional
    public void save(SaveNewMenuDTO menuDTO) {
        Objects.requireNonNull(menuDTO, "menu must not be null");

        //Récupération de l'actuel portant ce nom s'il existe
        //Optional<MenuJpa> menuJpa = menuJpaRepository.findMenuDetailsById(menuDTO.menuId());
        //if(menuJpa.isPresent()) {
        //    throw new DomainException("Menu already exists, use ModifyMenuUseCase");
        //}

        //Récupération des recettes du menu dans la base pour ne pas recréer les éléments existants
        Map<Long, RecipeJpa> recipeJpaList = new HashMap<>();
        for(SaveNewMenuDTO.saveNewMenuLine menuLine : menuDTO.menuLines()) {
            Optional<RecipeJpa> recipeJpa = recipeRepository.findJpaById(menuLine.recipeId());
            if(recipeJpa.isEmpty()) {
                throw new DomainException("Recipe " + menuLine.recipeId() +" not found in base");
            }
            else {
                recipeJpaList.put(recipeJpa.get().getId(), recipeJpa.get());
            }
        }

        //Fabrication des lignes du menu
        List<MenuLineJpa> menuLineJpaList = new ArrayList<>();
        for(SaveNewMenuDTO.saveNewMenuLine menuLine : menuDTO.menuLines()) {
            if(menuLine.nbPerson() == null || menuLine.nbPerson().compareTo(BigDecimal.ZERO) < 0) {
                throw new DomainException("nbPerson must not be null or <= 0");
            }
            menuLineJpaList.add(
                    new MenuLineJpa(
                            recipeJpaList.get(menuLine.recipeId()),
                            menuLine.nbPerson()
                    )
            );
        }

        //Fabrication du nouveau menu
        MenuJpa newMenuJpa = new MenuJpa();
        newMenuJpa.setName(menuDTO.menuName());

        for (MenuLineJpa menuLineJpa : menuLineJpaList) {
            newMenuJpa.addMenuLine(menuLineJpa);
        }

        //Sauvegarde du nouveau menu
        menuJpaRepository.save(newMenuJpa);
    }
}
