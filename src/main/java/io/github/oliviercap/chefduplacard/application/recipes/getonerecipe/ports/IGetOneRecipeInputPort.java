package io.github.oliviercap.chefduplacard.application.recipes.getonerecipe.ports;

import io.github.oliviercap.chefduplacard.application.recipes.getonerecipe.GetOneRecipeRequestModel;

public interface IGetOneRecipeInputPort {
    void execute(GetOneRecipeRequestModel requestModel);    
}
