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

import java.util.Optional;
import java.util.Set;
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
    public Optional<Menu> findByName(String menuName) {

        //Première requête, UN SEUL fetch d'une liste: liste des recettes du menu
        Optional<MenuJpa> menuJpaOptional = menuJpaRepository.findMenuDetailsByName(menuName);

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

}
