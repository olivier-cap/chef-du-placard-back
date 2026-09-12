package io.github.oliviercap.chefduplacard.adapters.web.menu.cookablemenus;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

public record CookableMenusViewModel(
        boolean covered,
        List<RecipeViewModel> recipes,
        List<RecipeViewModel> proposal,
        String message
) {
    public record RecipeViewModel(
            Long id,
            String name,
            String instructions,
            Duration duration,
            String difficulty,
            List<IngredientViewModel> ingredients
    ) {
    }

    public record IngredientViewModel(
            Long id,
            BigDecimal quantityPerPerson,
            String name,
            String symbol
    ) {
    }
}