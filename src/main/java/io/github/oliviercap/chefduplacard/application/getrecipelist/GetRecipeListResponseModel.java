package io.github.oliviercap.chefduplacard.application.getrecipelist;

import io.github.oliviercap.chefduplacard.application.dto.RecipeResponse;

import java.util.List;

public record GetRecipeListResponseModel(List<RecipeResponse> recipeResponseList) {
}
