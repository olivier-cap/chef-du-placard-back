package io.github.oliviercap.chefduplacard.application.dto;

import java.math.BigDecimal;

public record MenuLineResponse(
        RecipeResponse recipeResponse,
        BigDecimal nbPerson
) {
}
