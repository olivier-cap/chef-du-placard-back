package io.github.oliviercap.chefduplacard.application.getrecipelist.ports;

import io.github.oliviercap.chefduplacard.application.getrecipelist.GetRecipeListRequestModel;

public interface GetRecipeListInputPort {
    void execute(GetRecipeListRequestModel requestModel);
}
