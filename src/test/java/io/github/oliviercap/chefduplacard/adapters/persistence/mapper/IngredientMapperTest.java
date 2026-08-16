package io.github.oliviercap.chefduplacard.adapters.persistence.mapper;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.AlimentJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.IngredientJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.RecipeJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UnitJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.aliment.AlimentMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.ingredient.IngredientMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.unit.UnitMapper;
import io.github.oliviercap.chefduplacard.domain.food.Ingredient;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class IngredientMapperTest {

    @Test
    void creates_domain_ingredient_from_jpa() {
        AlimentJpa alimentJpa = new AlimentJpa(
                1L,
                "name",
                "description",
                true
        );

        UnitJpa unitJpa = new UnitJpa(
                2L,
                "name",
                "symbol"
        );

        RecipeJpa recipeJpa = new RecipeJpa(
                "recipe",
                "instructions",
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

        AlimentMapper alimentMapper = new AlimentMapper();
        UnitMapper unitMapper = new UnitMapper();

        IngredientMapper ingredientMapper = new IngredientMapper(
                alimentMapper,
                unitMapper
        );

        Ingredient result = ingredientMapper.toDomain(ingredientJpa);

        assertThat(result.getId().id())
                .isEqualTo(3L);

        assertThat(result.getQuantity())
                .isEqualByComparingTo(BigDecimal.ONE);

        assertThat(result.getAliment().getId().id())
                .isEqualTo(1L);

        assertThat(result.getAliment().getName())
                .isEqualTo("name");

        assertThat(result.getAliment().getDescription())
                .isEqualTo("description");

        assertThat(result.getAliment().isActive())
                .isTrue();

        assertThat(result.getUnit().getId().id())
                .isEqualTo(2L);

        assertThat(result.getUnit().getName())
                .isEqualTo("name");

        assertThat(result.getUnit().getSymbol())
                .isEqualTo("symbol");
    }
}