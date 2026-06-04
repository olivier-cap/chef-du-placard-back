package io.github.oliviercap.chefduplacard.application.dto;

import io.github.oliviercap.chefduplacard.domain.menu.MenuLine;

import java.math.BigDecimal;

public record MenuLineResponse(
        RecipeResponse recipeResponse,
        BigDecimal nbPerson
) {
    public static MenuLineResponse from(MenuLine menuLine) {
        return new MenuLineResponse(
                RecipeResponse.from(menuLine.getRecipe()),
                menuLine.getNbPerson()
        );
    }
}
