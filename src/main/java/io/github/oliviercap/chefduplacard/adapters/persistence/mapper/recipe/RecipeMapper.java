package io.github.oliviercap.chefduplacard.adapters.persistence.mapper.recipe;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.RecipeJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.ingredient.IngredientMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.recipe_type.RecipeTypeMapper;
import io.github.oliviercap.chefduplacard.domain.recipe.Recipe;
import io.github.oliviercap.chefduplacard.domain.recipe.RecipeId;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Objects;

@Component
public class RecipeMapper {

    private final IngredientMapper ingredientMapper;
    private final RecipeTypeMapper recipeTypeMapper;

    public RecipeMapper(IngredientMapper ingredientMapper, RecipeTypeMapper recipeTypeMapper) {

        this.ingredientMapper = ingredientMapper;
        this.recipeTypeMapper = recipeTypeMapper;
    }


    public Recipe toDomain(RecipeJpa recipeJpa) {
        Objects.requireNonNull(recipeJpa, "recipeJpa must not be null");

        return new Recipe(
                new RecipeId(recipeJpa.getId()),
                recipeJpa.getName(),
                recipeJpa.getInstructions(),
                Duration.ofMinutes(recipeJpa.getDurationMinutes() == null ? 0 : recipeJpa.getDurationMinutes()),
                recipeJpa.getDifficulty(),
                recipeJpa.getIngredients().stream()
                        .map(ingredientMapper::toDomain)
                        .toList(),
                recipeTypeMapper.toDomain(recipeJpa.getRecipeTypeJpa())
        );
    }
}
