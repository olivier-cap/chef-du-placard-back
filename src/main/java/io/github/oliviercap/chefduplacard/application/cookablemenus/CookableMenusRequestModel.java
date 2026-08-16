package io.github.oliviercap.chefduplacard.application.cookablemenus;

import io.github.oliviercap.chefduplacard.domain.recipefilters.RecipeFilter;

import java.util.List;

public record CookableMenusRequestModel(
        Long stockId,
        int nbMealToPrepare,
        int nbPeople,
        List<RecipeFilter> recipeFilters
) {
}
