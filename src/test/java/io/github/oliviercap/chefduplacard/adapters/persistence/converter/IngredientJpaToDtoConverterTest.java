package io.github.oliviercap.chefduplacard.adapters.persistence.converter;

import io.github.oliviercap.chefduplacard.adapters.persistence.converter.aliment.AlimentJpaToDtoConverter;
import io.github.oliviercap.chefduplacard.adapters.persistence.converter.aliment.IAlimentJpaToDtoConverter;
import io.github.oliviercap.chefduplacard.adapters.persistence.converter.ingredient.IIngredientJpaToDtoConverter;
import io.github.oliviercap.chefduplacard.adapters.persistence.converter.ingredient.IngredientJpaToDtoConverter;
import io.github.oliviercap.chefduplacard.adapters.persistence.converter.unit.IUnitJpaToDtoConverter;
import io.github.oliviercap.chefduplacard.adapters.persistence.converter.unit.UnitJpaToDtoConverter;
import io.github.oliviercap.chefduplacard.adapters.persistence.dto.AlimentDTO;
import io.github.oliviercap.chefduplacard.adapters.persistence.dto.IngredientDTO;
import io.github.oliviercap.chefduplacard.adapters.persistence.dto.UnitDTO;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.AlimentJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.IngredientJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.RecipeJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UnitJpa;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class IngredientJpaToDtoConverterTest {

    @Test
    void creates_ingredientDto_from_ingredientJpa(){
        AlimentJpa alimentJpa = new AlimentJpa("name", "description", true);
        UnitJpa  unitJpa = new UnitJpa("name", "symbol");
        RecipeJpa recipeJpa = new RecipeJpa("name","instructions",1,"difficulty");

        IngredientJpa ingredientJpa = new IngredientJpa(recipeJpa, alimentJpa, unitJpa, BigDecimal.valueOf(1));

        AlimentDTO alimentDTO = new AlimentDTO("name", "description", true);
        UnitDTO unitDTO = new UnitDTO("name","symbol");

        IAlimentJpaToDtoConverter alimentJpaToDtoConverter = new AlimentJpaToDtoConverter();
        IUnitJpaToDtoConverter unitJpaToDtoConverter = new UnitJpaToDtoConverter();
        IIngredientJpaToDtoConverter ingredientJpaToDtoConverter = new IngredientJpaToDtoConverter(alimentJpaToDtoConverter, unitJpaToDtoConverter);

        IngredientDTO expectedDTO = new IngredientDTO(BigDecimal.valueOf(1), alimentDTO, unitDTO);
        IngredientDTO ingredientDTO = ingredientJpaToDtoConverter.toDTO(ingredientJpa);

        assertThat(ingredientDTO).isEqualTo(expectedDTO);
    }


}
