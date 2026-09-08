package io.github.oliviercap.chefduplacard.application.htttpresponse;

import io.github.oliviercap.chefduplacard.domain.recipe_type.RecipeType;

public record RecipeTypeResponse(
        Long id,
        String name
) {
    public static RecipeTypeResponse from(RecipeType recipeType) {
        return new RecipeTypeResponse(
                recipeType.getId().id(),
                recipeType.getName()
        );
    }
}
