package io.github.oliviercap.chefduplacard.application.getrecipelist;

import io.github.oliviercap.chefduplacard.application.dto.RecipeResponse;
import io.github.oliviercap.chefduplacard.application.getrecipelist.ports.IGetRecipeListInputPort;
import io.github.oliviercap.chefduplacard.application.getrecipelist.ports.IGetRecipeListOutPort;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IRecipeRepository;
import io.github.oliviercap.chefduplacard.domain.recipe.Recipe;

import java.util.List;

/**
 * Propose une liste des recettes existantes. Ne donne pas tous les détails de chaque recette.
 */
public class GetRecipeListUseCase implements IGetRecipeListInputPort {

    private final IRecipeRepository recipeRepository;
    private final IGetRecipeListOutPort outPort;

    public GetRecipeListUseCase(IRecipeRepository recipeRepository,
                                IGetRecipeListOutPort outPort) {
        this.recipeRepository = recipeRepository;
        this.outPort = outPort;
    }

    @Override
    public void execute(GetRecipeListRequestModel requestModel) {
        List<RecipeResponse> recipeResponses = getRecipeList();
        outPort.displayRecipeList(new GetRecipeListResponseModel(recipeResponses));
    }

    private List<RecipeResponse> getRecipeList() {

        List<Recipe> recipeList = recipeRepository.findAll();

        return recipeList.stream()
                .map(RecipeResponse::from)
                .toList();
    }
}
