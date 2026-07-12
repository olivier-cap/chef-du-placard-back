package io.github.oliviercap.chefduplacard.application.createnewrecipe.ports;

import io.github.oliviercap.chefduplacard.application.createnewrecipe.CreateNewRecipeRequestModel;

public interface ICreateNewRecipeInputPort {
    void execute(CreateNewRecipeRequestModel requestModel);
}
