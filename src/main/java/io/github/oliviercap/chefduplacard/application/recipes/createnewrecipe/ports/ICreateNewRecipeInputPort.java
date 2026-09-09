package io.github.oliviercap.chefduplacard.application.recipes.createnewrecipe.ports;

import io.github.oliviercap.chefduplacard.application.recipes.createnewrecipe.CreateNewRecipeRequestModel;

public interface ICreateNewRecipeInputPort {
    void execute(CreateNewRecipeRequestModel requestModel);
}
