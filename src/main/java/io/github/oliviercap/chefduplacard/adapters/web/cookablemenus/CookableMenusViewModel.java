package io.github.oliviercap.chefduplacard.adapters.web.cookablemenus;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

public record CookableMenusViewModel(
        boolean covered,
        List<RecipeViewModel> recipes,
        String message
) {
    public record RecipeViewModel(
            Long recipeId,
            String recipeName,
            String recipeInstructions,
            Duration duration,
            String difficulty,
            List<IngredientViewModel> ingredients
    ) {
    }

    public record IngredientViewModel(
            Long ingredientId,
            BigDecimal quantityPerPerson,
            String alimentName,
            String unitSymbol
    ) {
    }
}