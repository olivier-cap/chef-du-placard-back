package io.github.oliviercap.chefduplacard.application.recipes.getrecipelist.ports;

import io.github.oliviercap.chefduplacard.adapters.web.recipes.getrecipelist.GetRecipeListViewModel;
import io.github.oliviercap.chefduplacard.application.recipes.getrecipelist.GetRecipeListResponseModel;

public interface IGetRecipeListOutPort {
    void displayRecipeList(GetRecipeListResponseModel responseModel);
    GetRecipeListViewModel getViewModel();
}
