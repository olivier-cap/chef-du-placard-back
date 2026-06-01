package io.github.oliviercap.chefduplacard.application.getonerecipe.ports;

public interface IGetOneRecipeOutputPort {
    void diplayOneRecipe(GetOneRecipeResponseModel responseModel);
    GetOneRecipeViewModel getViewModel();
}
