package io.github.oliviercap.chefduplacard.application.ports.persistence;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.RecipeJpa;
import io.github.oliviercap.chefduplacard.domain.recipe.Recipe;

import java.util.List;
import java.util.Optional;

public interface IRecipeRepository {
    List<Recipe> findAll();
    Optional<Recipe> findByName(String recipeName);
    Optional<RecipeJpa> findJpaById(Long id);
    Optional<RecipeJpa> findJpaByName(String recipeName);

}
