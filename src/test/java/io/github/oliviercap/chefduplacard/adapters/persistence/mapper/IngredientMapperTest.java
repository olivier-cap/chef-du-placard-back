package io.github.oliviercap.chefduplacard.adapters.persistence.mapper;

import io.github.oliviercap.chefduplacard.adapters.persistence.dto.AlimentDTO;
import io.github.oliviercap.chefduplacard.adapters.persistence.dto.IngredientDTO;
import io.github.oliviercap.chefduplacard.adapters.persistence.dto.UnitDTO;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.aliment.AlimentMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.aliment.IAlimentMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.ingredient.IIngredientMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.ingredient.IngredientMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.unit.IUnitMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.unit.UnitMapper;
import io.github.oliviercap.chefduplacard.domain.food.Aliment;
import io.github.oliviercap.chefduplacard.domain.food.Ingredient;
import io.github.oliviercap.chefduplacard.domain.unit.Unit;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class IngredientMapperTest {

    //Test transformation ingredient dto -> ingredient domain
    @Test
    void creates_domain_aliment_from_dto() {
         AlimentDTO alimentDTO = new AlimentDTO("name","description", true);
         UnitDTO unitDTO = new UnitDTO("name","symbol");
         IngredientDTO ingredientDTO = new IngredientDTO(BigDecimal.valueOf(1), alimentDTO, unitDTO);

         Aliment aliment = new Aliment("name", "description",true);
         Unit unit = new Unit("name","symbol");
         Ingredient ingredientExpected = new Ingredient(BigDecimal.valueOf(1), aliment, unit);

         IAlimentMapper alimentMapper = new AlimentMapper();
         IUnitMapper unitMapper = new UnitMapper();

         IIngredientMapper ingredientMapper = new IngredientMapper(alimentMapper, unitMapper);


         assertThat(ingredientMapper.toDomain(ingredientDTO).getAliment().getName()).isEqualTo(ingredientExpected.getAliment().getName());
         assertThat(ingredientMapper.toDomain(ingredientDTO)).isEqualTo(ingredientExpected);

     }
}
