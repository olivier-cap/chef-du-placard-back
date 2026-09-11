package io.github.oliviercap.chefduplacard.application.menu.cookablemenus;

import io.github.oliviercap.chefduplacard.application.htttpresponse.RecipeResponse;

import java.util.List;

public record CookableMenusResponseModel(
        boolean nbMealCovered,
        List<RecipeResponse> recipes,
        List<RecipeResponse> proposalRecipes,
        String message
) {
}
