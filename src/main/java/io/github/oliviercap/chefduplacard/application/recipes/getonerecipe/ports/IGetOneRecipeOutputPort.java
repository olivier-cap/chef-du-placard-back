package io.github.oliviercap.chefduplacard.application.recipes.getonerecipe.ports;

import io.github.oliviercap.chefduplacard.adapters.web.recipes.getonerecipe.GetOneRecipeViewModel;
import io.github.oliviercap.chefduplacard.application.recipes.getonerecipe.GetOneRecipeResponseModel;

public interface IGetOneRecipeOutputPort {
    void diplayOneRecipe(GetOneRecipeResponseModel responseModel);
    GetOneRecipeViewModel getViewModel();
}
