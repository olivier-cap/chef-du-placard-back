package io.github.oliviercap.chefduplacard.application.converter.reciperesponse;

import io.github.oliviercap.chefduplacard.application.dto.RecipeResponse;
import io.github.oliviercap.chefduplacard.domain.recipe.Recipe;

public interface IRecipeToRecipeResponse {
    public RecipeResponse toDTO(Recipe recipe);
}
