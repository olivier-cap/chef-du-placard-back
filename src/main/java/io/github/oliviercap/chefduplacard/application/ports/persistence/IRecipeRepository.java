package io.github.oliviercap.chefduplacard.application.ports.persistence;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.RecipeJpa;
import io.github.oliviercap.chefduplacard.application.createnewrecipe.IngredientsData;
import io.github.oliviercap.chefduplacard.domain.recipe.Recipe;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

public interface IRecipeRepository {
    List<Recipe> findAll();
    Optional<Recipe> findById(Long recipeId);

    void saveNew(
            String nameRecipe,
            String instructionsRecipe,
            Duration durationRecipe,
            String difficultyRecipe,
            List<IngredientsData> ingredients);
    RecipeJpa getReferenceJpaById(Long id);


    Optional<RecipeJpa> findJpaById(Long id);

}
