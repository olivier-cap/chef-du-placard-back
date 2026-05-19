package io.github.oliviercap.chefduplacard.application.cookablerecipes.ports;

import io.github.oliviercap.chefduplacard.application.cookablerecipes.FindCookableRecipesResponseModel;

public interface IFindCookableRecipesOutputPort {
    void displayCookableRecipes(FindCookableRecipesResponseModel responseModel);
}
