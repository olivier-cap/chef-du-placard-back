package io.github.oliviercap.chefduplacard.adapters.persistence.mapper;

import io.github.oliviercap.chefduplacard.adapters.persistence.dto.*;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.aliment.AlimentMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.aliment.IAlimentMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.ingredient.IIngredientMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.ingredient.IngredientMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.recipe.IRecipeMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.recipe.RecipeMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.stock.IStockMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.stock.StockMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.stockline.IStockLineMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.stockline.StockLineMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.unit.IUnitMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.unit.UnitMapper;
import io.github.oliviercap.chefduplacard.domain.food.Aliment;
import io.github.oliviercap.chefduplacard.domain.food.Ingredient;
import io.github.oliviercap.chefduplacard.domain.recipe.Recipe;
import io.github.oliviercap.chefduplacard.domain.stock.Stock;
import io.github.oliviercap.chefduplacard.domain.stock.StockLine;
import io.github.oliviercap.chefduplacard.domain.unit.Unit;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RecipeMapperTest {

    @Test
    void creates_stock_domain_from_dto(){
        AlimentDTO alimentDTO = new AlimentDTO("name","description", true);
        UnitDTO unitDTO = new UnitDTO("name","symbol");
        IngredientDTO ingredientDTO = new IngredientDTO(BigDecimal.valueOf(1), alimentDTO, unitDTO);
        RecipeDTO recipeDTO = new RecipeDTO("name", "instr", 1, "1", List.of(ingredientDTO));

        Aliment aliment = new Aliment("name", "description",true);
        Unit unit = new Unit("name","symbol");
        Ingredient ingredient = new Ingredient(BigDecimal.valueOf(1), aliment, unit);
        Recipe recipeExpected = new Recipe("name", "instr", Duration.ofMinutes(1), "1", List.of(ingredient));

        IAlimentMapper alimentMapper = new AlimentMapper();
        IUnitMapper unitMapper = new UnitMapper();
        IIngredientMapper ingredientMapper = new IngredientMapper(alimentMapper, unitMapper);
        IRecipeMapper recipeMapper = new RecipeMapper(ingredientMapper);

        Recipe recipe = recipeMapper.toDomain(recipeDTO);

        assertThat(recipe).isEqualTo(recipeExpected);

    }

}
