package io.github.oliviercap.chefduplacard.application.recipes.getrecipelist;

import java.util.List;

public record GetRecipeListResponseModel(List<GetRecipeListQuery> recipeViewList) {
}
