package io.github.oliviercap.chefduplacard.application.recipes.createnewrecipe.ports;

import io.github.oliviercap.chefduplacard.adapters.web.recipes.createnewrecipe.CreateNewRecipeViewModel;
import io.github.oliviercap.chefduplacard.application.recipes.createnewrecipe.CreateNewRecipeResponseModel;

public interface ICreateNewRecipeOutputPort {
    void newRecipeSaved(CreateNewRecipeResponseModel responseModel);
    CreateNewRecipeViewModel getViewModel();
}
