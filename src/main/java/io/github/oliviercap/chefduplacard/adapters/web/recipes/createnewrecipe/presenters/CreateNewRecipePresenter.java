package io.github.oliviercap.chefduplacard.adapters.web.recipes.createnewrecipe.presenters;

import io.github.oliviercap.chefduplacard.adapters.web.recipes.createnewrecipe.CreateNewRecipeViewModel;
import io.github.oliviercap.chefduplacard.application.recipes.createnewrecipe.CreateNewRecipeResponseModel;
import io.github.oliviercap.chefduplacard.application.recipes.createnewrecipe.ports.ICreateNewRecipeOutputPort;
import org.springframework.stereotype.Component;

@Component
public class CreateNewRecipePresenter implements ICreateNewRecipeOutputPort {

    private CreateNewRecipeViewModel viewModel;


    @Override
    public void newRecipeSaved(CreateNewRecipeResponseModel responseModel) {
        viewModel =  new CreateNewRecipeViewModel(responseModel.saved());
    }

    @Override
    public CreateNewRecipeViewModel getViewModel() {
        return viewModel;
    }
}
