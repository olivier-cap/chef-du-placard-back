package io.github.oliviercap.chefduplacard.application.cookablerecipes;

import io.github.oliviercap.chefduplacard.application.dto.RecipeResponse;

import java.util.List;

public record FindCookableRecipesResponseModel(List<RecipeResponse> recipeResponses) {
}
