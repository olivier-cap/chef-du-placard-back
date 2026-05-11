package io.github.oliviercap.chefduplacard.application.converter.reciperesponse;

import io.github.oliviercap.chefduplacard.application.converter.ingredientresponse.IIngredientToIngredientResponse;
import io.github.oliviercap.chefduplacard.application.dto.RecipeResponse;
import io.github.oliviercap.chefduplacard.domain.recipe.Recipe;
import org.springframework.stereotype.Component;

@Component
public class RecipeToRecipeResponse implements IRecipeToRecipeResponse {

    private final IIngredientToIngredientResponse ingredientToIngredientResponse;

    public RecipeToRecipeResponse(IIngredientToIngredientResponse ingredientToIngredientResponse) {
        this.ingredientToIngredientResponse = ingredientToIngredientResponse;
    }

    public RecipeResponse toDTO(Recipe recipe) {
        return new RecipeResponse(
                recipe.getName(),
                recipe.getInstructions(),
                recipe.getDuration(),
                recipe.getDifficulty(),
                recipe.getIngredients().stream()
                        .map(ingredientToIngredientResponse::toDTO)
                        .toList()
        );
    }
}
