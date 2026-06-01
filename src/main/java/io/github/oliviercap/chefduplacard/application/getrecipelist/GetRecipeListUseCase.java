package io.github.oliviercap.chefduplacard.application.getrecipelist;

import io.github.oliviercap.chefduplacard.application.dto.RecipeResponse;
import io.github.oliviercap.chefduplacard.application.getrecipelist.ports.GetRecipeListInputPort;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IRecipeRepository;
import io.github.oliviercap.chefduplacard.domain.recipe.Recipe;

import java.util.List;

/**
 * Propose une liste des recettes existantes. Ne donne pas tous les détails de chaque recette.
 */
public class GetRecipeListUseCase implements GetRecipeListInputPort {

    private final IRecipeRepository recipeRepository;

    public GetRecipeListUseCase(IRecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void execute(GetRecipeListRequestModel requestModel) {
        List<RecipeResponse> recipeResponses = getRecipeList();
    }

    private List<RecipeResponse> getRecipeList() {

        List<Recipe> recipeList = recipeRepository.findAll();

        return recipeList.stream()
                .map(RecipeResponse::from)
                .toList();
    }
}
