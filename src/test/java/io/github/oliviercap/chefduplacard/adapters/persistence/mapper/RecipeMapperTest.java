package io.github.oliviercap.chefduplacard.adapters.persistence.mapper;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.AlimentJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.IngredientJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.RecipeJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UnitJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.aliment.AlimentMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.ingredient.IngredientMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.recipe.RecipeMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.unit.UnitMapper;
import io.github.oliviercap.chefduplacard.domain.food.Aliment;
import io.github.oliviercap.chefduplacard.domain.food.Ingredient;
import io.github.oliviercap.chefduplacard.domain.recipe.Recipe;
import io.github.oliviercap.chefduplacard.domain.unit.Unit;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RecipeMapperTest {

    @Test
    void creates_stock_domain_from_jpa(){
        AlimentJpa alimentJpa = new AlimentJpa("name","description", true);
        UnitJpa unitJpa = new UnitJpa("name","symbol");
        RecipeJpa recipeJpa = new RecipeJpa("name", "instr", 1, "1");
        IngredientJpa ingredientJpa = new IngredientJpa(recipeJpa, alimentJpa, unitJpa, BigDecimal.valueOf(1));

        recipeJpa.addIngredient(ingredientJpa);

        Aliment aliment = new Aliment("name", "description",true);
        Unit unit = new Unit("name","symbol");
        Ingredient ingredient = new Ingredient(BigDecimal.valueOf(1), aliment, unit);
        Recipe recipeExpected = new Recipe("name", "instr", Duration.ofMinutes(1), "1", List.of(ingredient));

        AlimentMapper alimentMapper = new AlimentMapper();
        UnitMapper unitMapper = new UnitMapper();
        IngredientMapper ingredientMapper = new IngredientMapper(alimentMapper, unitMapper);
        RecipeMapper recipeMapper = new RecipeMapper(ingredientMapper);

        Recipe recipe = recipeMapper.toDomain(recipeJpa);

        assertThat(recipe).isEqualTo(recipeExpected);
    }
}
