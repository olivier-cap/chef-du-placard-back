package io.github.oliviercap.chefduplacard.application.getrecipelist;

import io.github.oliviercap.chefduplacard.application.htttpresponse.RecipeResponse;
import io.github.oliviercap.chefduplacard.application.getrecipelist.ports.IGetRecipeListInputPort;
import io.github.oliviercap.chefduplacard.application.getrecipelist.ports.IGetRecipeListOutPort;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IRecipeRepository;
import io.github.oliviercap.chefduplacard.application.ports.query.IGetRecipeListViewQuery;
import io.github.oliviercap.chefduplacard.domain.recipe.Recipe;

import java.util.List;

/**
 * Propose une liste des recettes existantes. Ne donne pas tous les détails de chaque recette.
 */
public class GetRecipeListUseCase implements IGetRecipeListInputPort {

    //private final IRecipeRepository recipeRepository;
    private final IGetRecipeListViewQuery getRecipeListViewQuery;
    private final IGetRecipeListOutPort outPort;

    public GetRecipeListUseCase(IGetRecipeListViewQuery getRecipeListViewQuery,
                                IGetRecipeListOutPort outPort) {
        this.getRecipeListViewQuery = getRecipeListViewQuery;
        this.outPort = outPort;
    }

    @Override
    public void execute(GetRecipeListRequestModel requestModel) {
        //List<RecipeResponse> recipeResponses = getRecipeList();
        //outPort.displayRecipeList(new GetRecipeListResponseModel(recipeResponses));
        outPort.displayRecipeList(new GetRecipeListResponseModel(getRecipeList()));
    }

    //private List<RecipeResponse> getRecipeList() {
    private List<GetRecipeListQuery> getRecipeList() {
        return getRecipeListViewQuery.getRecipeListQuery();
        /*
        List<Recipe> recipeList = recipeRepository.findAll();

        return recipeList.stream()
                .map(RecipeResponse::from)
                .toList();

 */
    }
}
