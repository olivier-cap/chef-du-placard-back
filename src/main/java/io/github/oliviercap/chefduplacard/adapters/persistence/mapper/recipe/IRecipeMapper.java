package io.github.oliviercap.chefduplacard.adapters.persistence.mapper.recipe;

import io.github.oliviercap.chefduplacard.adapters.persistence.dto.RecipeDTO;
import io.github.oliviercap.chefduplacard.domain.recipe.Recipe;

public interface IRecipeMapper {
    Recipe toDomain(RecipeDTO recipeDTO);
}
