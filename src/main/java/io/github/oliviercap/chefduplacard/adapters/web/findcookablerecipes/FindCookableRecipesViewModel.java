package io.github.oliviercap.chefduplacard.adapters.web.findcookablerecipes;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

public record FindCookableRecipesViewModel(
        List<RecipeViewModel> recipes
) {
    public record RecipeViewModel(
            String recipeName,
            String recipeInstructions,
            Duration duration,
            String difficulty,
            List<IngredientViewModel> ingredients
    ) {
    }

    public record IngredientViewModel(
            BigDecimal quantityPerPerson,
            String alimentName,
            String unitSymbol
    ) {
    }
}