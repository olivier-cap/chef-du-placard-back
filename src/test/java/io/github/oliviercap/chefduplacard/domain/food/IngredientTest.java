package io.github.oliviercap.chefduplacard.domain.food;

import io.github.oliviercap.chefduplacard.domain.unit.Unit;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class IngredientTest {

    @Test
    void ingredient_can_be_create_and_aliment_modified(){
        //GIVEN
        Aliment apple = new Aliment("fruit", "pomme", true);
        Aliment grapefruit = new Aliment("fruit", "grapefruit", true);
        Unit unit = new Unit("gramme","g");
        BigDecimal quantity = BigDecimal.valueOf(12);

        Ingredient ingredient = new Ingredient(quantity,apple, unit);

        //WHEN
        ingredient.setAliment(grapefruit);

        //THEN
        assertThat(ingredient).isNotNull();
        assertThat(ingredient.getAliment()).isEqualTo(grapefruit);
    }
}
