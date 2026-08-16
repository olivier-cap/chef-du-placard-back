package io.github.oliviercap.chefduplacard.application.getonerecipe.ports;

import io.github.oliviercap.chefduplacard.application.getonerecipe.GetOneRecipeRequestModel;

public interface IGetOneRecipeInputPort {
    void execute(GetOneRecipeRequestModel requestModel);    
}
