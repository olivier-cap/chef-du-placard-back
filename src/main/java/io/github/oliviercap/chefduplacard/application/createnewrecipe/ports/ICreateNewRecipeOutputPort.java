package io.github.oliviercap.chefduplacard.application.createnewrecipe.ports;

import io.github.oliviercap.chefduplacard.adapters.web.createnewrecipe.CreateNewRecipeViewModel;
import io.github.oliviercap.chefduplacard.application.createnewrecipe.CreateNewRecipeResponseModel;

public interface ICreateNewRecipeOutputPort {
    void newRecipeSaved(CreateNewRecipeResponseModel responseModel);
    CreateNewRecipeViewModel getViewModel();
}
