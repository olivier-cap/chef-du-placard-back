    package io.github.oliviercap.chefduplacard.application.dto;

    import java.time.Duration;
    import java.util.List;

    public record RecipeResponse(
            String name,
            String instructions,
            Duration duration,
            String difficulty,
            List<IngredientResponse> ingredients
    ) {}