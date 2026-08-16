package io.github.oliviercap.chefduplacard.application.getonerecipe;

import io.github.oliviercap.chefduplacard.application.htttpresponse.RecipeResponse;
import io.github.oliviercap.chefduplacard.application.getonerecipe.ports.IGetOneRecipeInputPort;
import io.github.oliviercap.chefduplacard.application.getonerecipe.ports.IGetOneRecipeOutputPort;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IRecipeRepository;
import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import io.github.oliviercap.chefduplacard.domain.recipe.Recipe;

public class GetOneRecipeUseCase implements IGetOneRecipeInputPort {

    private final IRecipeRepository recipeRepository;
    private final IGetOneRecipeOutputPort outputPort;

    public GetOneRecipeUseCase(IRecipeRepository recipeRepository,
                               IGetOneRecipeOutputPort outputPort) {
        this.recipeRepository = recipeRepository;
        this.outputPort = outputPort;
    }

    @Override
    public void execute(GetOneRecipeRequestModel requestModel) {
        RecipeResponse recipeResponse = getOneRecipe(requestModel.recipeId());
        outputPort.diplayOneRecipe(new GetOneRecipeResponseModel(recipeResponse));
    }

    private RecipeResponse getOneRecipe(Long recipeId) {
        if (recipeId == null) {
            throw new DomainException("recipeId must not be null");
        }

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(()-> new DomainException("recipe not found " + recipeId));

        return RecipeResponse.from(recipe);
    }
}
