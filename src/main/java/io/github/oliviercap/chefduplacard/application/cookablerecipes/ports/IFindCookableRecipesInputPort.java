package io.github.oliviercap.chefduplacard.application.cookablerecipes.ports;


import io.github.oliviercap.chefduplacard.application.cookablerecipes.FindCookableRecipesRequestModel;

public interface IFindCookableRecipesInputPort {
    public void execute(FindCookableRecipesRequestModel findCookableRecipesRequestModel);
}
