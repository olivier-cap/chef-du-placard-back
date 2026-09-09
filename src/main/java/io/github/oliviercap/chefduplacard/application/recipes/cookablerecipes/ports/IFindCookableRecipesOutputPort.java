package io.github.oliviercap.chefduplacard.application.recipes.cookablerecipes.ports;

import io.github.oliviercap.chefduplacard.application.recipes.cookablerecipes.FindCookableRecipesResponseModel;

public interface IFindCookableRecipesOutputPort {
    void displayCookableRecipes(FindCookableRecipesResponseModel responseModel);
}
