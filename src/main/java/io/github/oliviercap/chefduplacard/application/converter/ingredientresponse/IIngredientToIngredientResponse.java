package io.github.oliviercap.chefduplacard.application.converter.ingredientresponse;

import io.github.oliviercap.chefduplacard.application.dto.IngredientResponse;
import io.github.oliviercap.chefduplacard.domain.food.Ingredient;

public interface IIngredientToIngredientResponse {
    public IngredientResponse toDTO(Ingredient ingredient);
}
