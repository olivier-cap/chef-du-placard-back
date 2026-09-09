package io.github.oliviercap.chefduplacard.application.recipes.cookablerecipes;

import io.github.oliviercap.chefduplacard.application.htttpresponse.RecipeResponse;

import java.util.List;

public record FindCookableRecipesResponseModel(List<RecipeResponse> recipeResponses) {
}
