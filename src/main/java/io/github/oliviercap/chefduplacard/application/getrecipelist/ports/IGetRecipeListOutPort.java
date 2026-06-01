package io.github.oliviercap.chefduplacard.application.getrecipelist.ports;

import io.github.oliviercap.chefduplacard.adapters.web.getrecipelist.GetRecipeListViewModel;
import io.github.oliviercap.chefduplacard.application.getrecipelist.GetRecipeListResponseModel;

public interface IGetRecipeListOutPort {
    void displayRecipeList(GetRecipeListResponseModel responseModel);
    GetRecipeListViewModel getViewModel();
}
