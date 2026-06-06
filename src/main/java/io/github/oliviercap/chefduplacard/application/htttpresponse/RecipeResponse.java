    package io.github.oliviercap.chefduplacard.application.htttpresponse;

    import io.github.oliviercap.chefduplacard.domain.recipe.Recipe;

    import java.time.Duration;
    import java.util.List;
    import java.util.Objects;

    public record RecipeResponse(
            String name,
            String instructions,
            Duration duration,
            String difficulty,
            List<IngredientResponse> ingredients
    ) {
        public static RecipeResponse from(Recipe recipe) {
            Objects.requireNonNull(recipe, "recipe must not be null");

            return new RecipeResponse(
                    recipe.getName(),
                    recipe.getInstructions(),
                    recipe.getDuration(),
                    recipe.getDifficulty(),
                    recipe.getIngredients().stream()
                            .map(IngredientResponse::from)
                            .toList()
            );
        }
    }