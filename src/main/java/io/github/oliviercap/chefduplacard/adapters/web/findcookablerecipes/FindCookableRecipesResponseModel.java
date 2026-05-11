package io.github.oliviercap.chefduplacard.adapters.web.findcookablerecipes;

import io.github.oliviercap.chefduplacard.adapters.web.findcookablerecipes.presenters.dto.RecipeForPresenter;

import java.util.List;

public record FindCookableRecipesResponseModel(List<RecipeForPresenter> recipes) {
}