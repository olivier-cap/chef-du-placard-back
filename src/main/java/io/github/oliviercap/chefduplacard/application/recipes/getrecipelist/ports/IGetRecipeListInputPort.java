package io.github.oliviercap.chefduplacard.application.recipes.getrecipelist.ports;

import io.github.oliviercap.chefduplacard.application.recipes.getrecipelist.GetRecipeListRequestModel;

public interface IGetRecipeListInputPort {
    void execute(GetRecipeListRequestModel requestModel);
}
