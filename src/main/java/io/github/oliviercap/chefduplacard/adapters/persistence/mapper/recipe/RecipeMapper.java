package io.github.oliviercap.chefduplacard.adapters.persistence.mapper.recipe;

import io.github.oliviercap.chefduplacard.adapters.persistence.dto.RecipeDTO;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.ingredient.IIngredientMapper;
import io.github.oliviercap.chefduplacard.domain.recipe.Recipe;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RecipeMapper implements IRecipeMapper{
    private final IIngredientMapper ingredientMapper;

    public RecipeMapper(IIngredientMapper ingredientMapper) {
        
        this.ingredientMapper = ingredientMapper;
    }


    @Override
    public Recipe toDomain(RecipeDTO recipeDTO) {
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
