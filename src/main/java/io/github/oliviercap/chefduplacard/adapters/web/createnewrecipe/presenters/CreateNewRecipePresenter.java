package io.github.oliviercap.chefduplacard.adapters.web.createnewrecipe.presenters;

import io.github.oliviercap.chefduplacard.adapters.web.createnewrecipe.CreateNewRecipeViewModel;
import io.github.oliviercap.chefduplacard.application.createnewrecipe.CreateNewRecipeResponseModel;
import io.github.oliviercap.chefduplacard.application.createnewrecipe.ports.ICreateNewRecipeOutputPort;
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
