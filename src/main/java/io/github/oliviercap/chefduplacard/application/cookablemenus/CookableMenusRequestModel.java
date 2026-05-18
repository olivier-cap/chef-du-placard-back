package io.github.oliviercap.chefduplacard.application.cookablemenus;

import io.github.oliviercap.chefduplacard.domain.recipefilters.RecipeFilter;

import java.util.List;

public record CookableMenusRequestModel(
        String stockName,
        int nbMealToPrepare,
        int nbPeople,
        List<RecipeFilter> recipeFilters
) {
}
