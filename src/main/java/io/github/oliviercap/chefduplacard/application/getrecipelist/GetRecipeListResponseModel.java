package io.github.oliviercap.chefduplacard.application.getrecipelist;

import java.util.List;

public record GetRecipeListResponseModel(List<GetRecipeListQuery> recipeViewList) {
}
