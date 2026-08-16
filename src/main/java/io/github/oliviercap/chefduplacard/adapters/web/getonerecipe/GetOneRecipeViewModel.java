    package io.github.oliviercap.chefduplacard.adapters.web.getonerecipe;

    import java.math.BigDecimal;
    import java.time.Duration;
    import java.util.List;

    public record GetOneRecipeViewModel(
            Long id,
            String name,
            String instructions,
            Duration duration,
            String difficulty,
            List<IngredientViewModel>ingredients
    ) {
        public record IngredientViewModel(
                Long id,
                BigDecimal quantity,
                AlimentViewModel aliment,
                UnitViewModel unit
        ) {}
        public record AlimentViewModel(
           Long id,
           String name,
           String description,
           boolean isActive
        ) {}
        public record UnitViewModel(
                Long id,
                String name,
                String symbol
        ) {}
    }
