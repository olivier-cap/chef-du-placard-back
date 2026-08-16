package io.github.oliviercap.chefduplacard.application.htttpresponse;

import io.github.oliviercap.chefduplacard.domain.menu.MenuLine;

import java.math.BigDecimal;

public record MenuLineResponse(
        Long id,
        RecipeResponse recipeResponse,
        BigDecimal nbPerson
) {
    public static MenuLineResponse from(MenuLine menuLine) {
        return new MenuLineResponse(
                menuLine.getId().id(),
                RecipeResponse.from(menuLine.getRecipe()),
                menuLine.getNbPerson()
        );
    }
}
