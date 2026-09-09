package io.github.oliviercap.chefduplacard.application.recipes.cookablerecipes.ports;


import io.github.oliviercap.chefduplacard.application.recipes.cookablerecipes.FindCookableRecipesRequestModel;

public interface IFindCookableRecipesInputPort {
    public void execute(FindCookableRecipesRequestModel findCookableRecipesRequestModel);
}
