package io.github.oliviercap.chefduplacard.application.getonerecipe;

import io.github.oliviercap.chefduplacard.application.dto.RecipeResponse;
import io.github.oliviercap.chefduplacard.application.getonerecipe.ports.IGetOneRecipeInputPort;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IRecipeRepository;
import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import io.github.oliviercap.chefduplacard.domain.recipe.Recipe;

public class GetOneRecipeUseCase implements IGetOneRecipeInputPort {

    private final IRecipeRepository recipeRepository;

    public GetOneRecipeUseCase(IRecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void execute(GetOneRecipeRequestModel requestModel) {
        RecipeResponse recipeResponse = getOneRecipe(requestModel.recipeName());

    }

    private RecipeResponse getOneRecipe(String recipeName) {
        if (recipeName.isBlank()) {
            throw new DomainException("recipeName must not be blank");
        }

        Recipe recipe = recipeRepository.findByName(recipeName)
                .orElseThrow(()-> new DomainException("recipe not found " + recipeName));

        return RecipeResponse.from(recipe);
    }
}
