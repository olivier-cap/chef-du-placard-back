package io.github.oliviercap.chefduplacard.application.menu.cookablemenus;

import io.github.oliviercap.chefduplacard.domain.recipe.Recipe;

import java.util.List;

public record CookableMenus(List<Recipe> recipesStockOk, List<Recipe> recipesNoStock) {
}
