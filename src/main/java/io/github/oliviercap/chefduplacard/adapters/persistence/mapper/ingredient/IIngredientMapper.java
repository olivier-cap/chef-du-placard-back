package io.github.oliviercap.chefduplacard.adapters.persistence.mapper.ingredient;

import io.github.oliviercap.chefduplacard.adapters.persistence.dto.IngredientDTO;
import io.github.oliviercap.chefduplacard.domain.food.Ingredient;

public interface IIngredientMapper {
    Ingredient toDomain(IngredientDTO ingredientDTO);
}
