package io.github.oliviercap.chefduplacard.adapters.web.findcookablerecipes.presenters;

import io.github.oliviercap.chefduplacard.adapters.web.findcookablerecipes.FindCookableRecipesResponseModel;
import io.github.oliviercap.chefduplacard.application.dto.RecipeResponse;

import java.util.List;

public interface IFindCookableRecipesOutputPort {
    FindCookableRecipesResponseModel displayCookableRecipes(List<RecipeResponse> recipeResponse);
}
