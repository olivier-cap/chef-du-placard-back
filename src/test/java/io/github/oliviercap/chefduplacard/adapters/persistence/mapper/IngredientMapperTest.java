package io.github.oliviercap.chefduplacard.adapters.persistence.mapper;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.AlimentJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.IngredientJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.RecipeJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UnitJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.aliment.AlimentMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.ingredient.IngredientMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.unit.UnitMapper;
import io.github.oliviercap.chefduplacard.domain.food.Aliment;
import io.github.oliviercap.chefduplacard.domain.food.Ingredient;
import io.github.oliviercap.chefduplacard.domain.unit.Unit;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class IngredientMapperTest {

    //Test transformation ingredient jpa -> ingredient domain
    @Test
    void creates_domain_aliment_from_jpa() {
         AlimentJpa alimentJpa = new AlimentJpa("name","description", true);
         UnitJpa unitJpa = new UnitJpa("name","symbol");
         RecipeJpa recipeJpa = new RecipeJpa("","",1,"");
         IngredientJpa ingredientJpa = new IngredientJpa(recipeJpa, alimentJpa, unitJpa, BigDecimal.valueOf(1));

         Aliment aliment = new Aliment("name", "description",true);
         Unit unit = new Unit("name","symbol");
         Ingredient ingredientExpected = new Ingredient(BigDecimal.valueOf(1), aliment, unit);

         AlimentMapper alimentMapper = new AlimentMapper();
         UnitMapper unitMapper = new UnitMapper();

         IngredientMapper ingredientMapper = new IngredientMapper(alimentMapper, unitMapper);

         assertThat(ingredientMapper.toDomain(ingredientJpa).getAliment().getName()).isEqualTo(ingredientExpected.getAliment().getName());
         assertThat(ingredientMapper.toDomain(ingredientJpa)).isEqualTo(ingredientExpected);
     }
}
