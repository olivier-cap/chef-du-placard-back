package io.github.oliviercap.chefduplacard.adapters.persistence.mapper.recipe;

import io.github.oliviercap.chefduplacard.adapters.persistence.dto.RecipeDTO;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.ingredient.IIngredientMapper;
import io.github.oliviercap.chefduplacard.domain.recipe.Recipe;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Objects;

@Component
public class RecipeMapper implements IRecipeMapper{
    private final IIngredientMapper ingredientMapper;

    public RecipeMapper(IIngredientMapper ingredientMapper) {
        this.ingredientMapper = ingredientMapper;
    }


    @Override
    public Recipe toDomain(RecipeDTO recipeDTO) {
        Objects.requireNonNull(recipeDTO, "recipeDTO must not be null");
        Objects.requireNonNull(recipeDTO.ingredients(), "ingredientsDTO must not be null");

        return new Recipe(
                recipeDTO.name(),
                recipeDTO.instructions(),
                Duration.ofMinutes(recipeDTO.duration()),
                recipeDTO.difficulty(),
                recipeDTO.ingredients().stream()
                        .map(ingredientMapper::toDomain)
                        .toList()
        );
    }
}
