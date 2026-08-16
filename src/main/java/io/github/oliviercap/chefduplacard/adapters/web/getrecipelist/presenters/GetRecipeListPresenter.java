package io.github.oliviercap.chefduplacard.adapters.web.getrecipelist.presenters;

import io.github.oliviercap.chefduplacard.adapters.web.getrecipelist.GetRecipeListViewModel;
import io.github.oliviercap.chefduplacard.application.getrecipelist.GetRecipeListQuery;
import io.github.oliviercap.chefduplacard.application.getrecipelist.GetRecipeListResponseModel;
import io.github.oliviercap.chefduplacard.application.getrecipelist.ports.IGetRecipeListOutPort;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class GetRecipeListPresenter implements IGetRecipeListOutPort {

    private GetRecipeListViewModel viewModel;


    @Override
    public void displayRecipeList(GetRecipeListResponseModel responseModel) {
        viewModel = new GetRecipeListViewModel(responseModel.recipeViewList().stream()
                .map(this::toViewModel)
                .toList()
        );
    }

    @Override
    public GetRecipeListViewModel getViewModel() {
        return viewModel;
    }

    private GetRecipeListViewModel.RecipeList toViewModel(GetRecipeListQuery recipeQuery) {
        return new GetRecipeListViewModel.RecipeList(
                recipeQuery.id(),
                recipeQuery.name(),
                Duration.ofMinutes(recipeQuery.duration()),
                recipeQuery.difficulty()
        );
    }
}
