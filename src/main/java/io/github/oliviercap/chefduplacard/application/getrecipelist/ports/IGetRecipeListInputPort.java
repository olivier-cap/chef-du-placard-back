package io.github.oliviercap.chefduplacard.application.getrecipelist.ports;

import io.github.oliviercap.chefduplacard.application.getrecipelist.GetRecipeListRequestModel;

public interface IGetRecipeListInputPort {
    void execute(GetRecipeListRequestModel requestModel);
}
