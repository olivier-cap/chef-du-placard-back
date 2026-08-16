package io.github.oliviercap.chefduplacard.application.htttpresponse;

import io.github.oliviercap.chefduplacard.domain.food.Ingredient;

import java.math.BigDecimal;
import java.util.Objects;

public record IngredientResponse(
        Long id,
        BigDecimal quantityPerPerson,
        AlimentResponse alimentResponse,
        UnitResponse unitResponse
){
    public static IngredientResponse from(Ingredient ingredient) {
        Objects.requireNonNull(ingredient, "ingredient must not be null");
        Objects.requireNonNull(ingredient.getAliment(), "aliment must not be null");
        Objects.requireNonNull(ingredient.getUnit(), "unit must not be null");

        return new IngredientResponse(
                ingredient.getId().id(),
                ingredient.getQuantity(),
                AlimentResponse.from(ingredient.getAliment()),
                UnitResponse.from(ingredient.getUnit())
        );
    }
}