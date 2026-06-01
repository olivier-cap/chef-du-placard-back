package io.github.oliviercap.chefduplacard.adapters.web.getrecipelist.presenters;

import io.github.oliviercap.chefduplacard.adapters.web.getrecipelist.GetRecipeListViewModel;
import io.github.oliviercap.chefduplacard.application.dto.RecipeResponse;
import io.github.oliviercap.chefduplacard.application.getrecipelist.GetRecipeListResponseModel;
import io.github.oliviercap.chefduplacard.application.getrecipelist.GetRecipeListUseCase;
import io.github.oliviercap.chefduplacard.application.getrecipelist.ports.IGetRecipeListOutPort;
import org.springframework.stereotype.Component;

@Component
public class GetRecipeListPresenter implements IGetRecipeListOutPort {

    private GetRecipeListViewModel viewModel;


    @Override
    public void displayRecipeList(GetRecipeListResponseModel responseModel) {
        viewModel = new GetRecipeListViewModel(responseModel.recipeResponseList().stream()
                .map(this::toViewModel)
                .toList()
        );
    }

    @Override
    public GetRecipeListViewModel getViewModel() {
        return viewModel;
    }

    private GetRecipeListViewModel.RecipeList toViewModel(RecipeResponse recipeResponse) {
        return new GetRecipeListViewModel.RecipeList(
                recipeResponse.name(),
                recipeResponse.duration(),
                recipeResponse.difficulty()
        );
    }
}
