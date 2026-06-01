package io.github.oliviercap.chefduplacard.adapters.web.getonerecipe;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

public record GetOneRecipeViewModel(
        String name,
        String instructions,
        Duration duration,
        String difficulty,
        List<IngredientViewModel>ingredients
) {
    public record IngredientViewModel(
            BigDecimal quantity,
            AlimentViewModel aliment,
            UnitViewModel unit
    ) {}
    public record AlimentViewModel(
       String name,
       String description,
       boolean isActive
    ) {}
    public record UnitViewModel(
            String name,
            String symbol
    ) {}
}
