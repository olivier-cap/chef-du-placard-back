package io.github.oliviercap.chefduplacard.application.getonerecipe.ports;

import io.github.oliviercap.chefduplacard.adapters.web.getonerecipe.GetOneRecipeViewModel;
import io.github.oliviercap.chefduplacard.application.getonerecipe.GetOneRecipeResponseModel;

public interface IGetOneRecipeOutputPort {
    void diplayOneRecipe(GetOneRecipeResponseModel responseModel);
    GetOneRecipeViewModel getViewModel();
}
