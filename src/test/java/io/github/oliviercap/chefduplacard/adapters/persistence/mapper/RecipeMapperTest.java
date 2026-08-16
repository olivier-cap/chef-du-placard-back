package io.github.oliviercap.chefduplacard.adapters.persistence.mapper;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.AlimentJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.IngredientJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.RecipeJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UnitJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.aliment.AlimentMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.ingredient.IngredientMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.recipe.RecipeMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.unit.UnitMapper;
import io.github.oliviercap.chefduplacard.domain.food.Ingredient;
import io.github.oliviercap.chefduplacard.domain.recipe.Recipe;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class RecipeMapperTest {

    @Test
    void creates_recipe_domain_from_jpa() {
        AlimentJpa alimentJpa = new AlimentJpa(
                1L,
                "aliment name",
                "aliment description",
                true
        );

        UnitJpa unitJpa = new UnitJpa(
                2L,
                "unit name",
                "symbol"
        );

        RecipeJpa recipeJpa = new RecipeJpa(
                4L,
                "recipe name",
                "recipe instructions",
                30,
                "easy"
        );

        IngredientJpa ingredientJpa = new IngredientJpa(
                3L,
                recipeJpa,
                alimentJpa,
                unitJpa,
                BigDecimal.ONE
        );

        recipeJpa.addIngredient(ingredientJpa);

        AlimentMapper alimentMapper = new AlimentMapper();
        UnitMapper unitMapper = new UnitMapper();

        IngredientMapper ingredientMapper = new IngredientMapper(
                alimentMapper,
                unitMapper
        );

        RecipeMapper recipeMapper = new RecipeMapper(
                ingredientMapper
        );

        Recipe result = recipeMapper.toDomain(recipeJpa);

        assertThat(result.getId().id())
                .isEqualTo(4L);

        assertThat(result.getName())
                .isEqualTo("recipe name");

        assertThat(result.getInstructions())
                .isEqualTo("recipe instructions");

        assertThat(result.getDuration())
                .isEqualTo(Duration.ofMinutes(30));

        assertThat(result.getDifficulty())
                .isEqualTo("easy");

        assertThat(result.getIngredients())
                .hasSize(1);

        Ingredient mappedIngredient = result
                .getIngredients()
                .getFirst();

        assertThat(mappedIngredient.getId().id())
                .isEqualTo(3L);

        assertThat(mappedIngredient.getQuantity())
                .isEqualByComparingTo(BigDecimal.ONE);

        assertThat(mappedIngredient.getAliment().getId().id())
                .isEqualTo(1L);

        assertThat(mappedIngredient.getAliment().getName())
                .isEqualTo("aliment name");

        assertThat(mappedIngredient.getAliment().getDescription())
                .isEqualTo("aliment description");

        assertThat(mappedIngredient.getAliment().isActive())
                .isTrue();

        assertThat(mappedIngredient.getUnit().getId().id())
                .isEqualTo(2L);

        assertThat(mappedIngredient.getUnit().getName())
                .isEqualTo("unit name");

        assertThat(mappedIngredient.getUnit().getSymbol())
                .isEqualTo("symbol");
    }
}