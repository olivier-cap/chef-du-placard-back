package io.github.oliviercap.chefduplacard.adapters.web.findcookablerecipes.presenters.dto;

import java.time.Duration;
import java.util.List;

public record RecipeForPresenter(String recipeName,
                                 String recipeInstructions,
                                 Duration duration,
                                 String difficulty,
                                 List<IngredientForPresenter> ingredients
) {
}
