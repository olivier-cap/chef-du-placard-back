package io.github.oliviercap.chefduplacard.adapters.persistence.mapper.recipe;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.RecipeJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.ingredient.IngredientMapper;
import io.github.oliviercap.chefduplacard.domain.recipe.Recipe;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Objects;

@Component
public class RecipeMapper {

    private final IngredientMapper ingredientMapper;

    public RecipeMapper(IngredientMapper ingredientMapper) {

        this.ingredientMapper = ingredientMapper;
    }


    public Recipe toDomain(RecipeJpa recipeJpa) {
        Objects.requireNonNull(recipeJpa, "recipeJpa must not be null");

        return new Recipe(
                recipeJpa.getName(),
                recipeJpa.getInstructions(),
                Duration.ofMinutes(recipeJpa.getDurationMinutes() == null ? 0 : recipeJpa.getDurationMinutes()),
                recipeJpa.getDifficulty(),
                recipeJpa.getIngredients().stream()
                        .map(ingredientMapper::toDomain)
                        .toList()
        );
    }
}
