package io.github.oliviercap.chefduplacard.domain.food;

import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import io.github.oliviercap.chefduplacard.domain.unit.Unit;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class IngredientTest {

    Aliment apple = new Aliment("apple","", true);
    Unit gram = new Unit("gram","g");

    @Test
    void should_create_valid_ingredient() {
        Ingredient ingredient = new Ingredient(
                BigDecimal.valueOf(2),
                apple,
                gram
        );
    }

    @Test
    void should_reject_null_quantity() {
        assertThatThrownBy(() ->
                new Ingredient(null, apple, gram)
        ).isInstanceOf(DomainException.class);
    }

    @Test
    void should_reject_negative_quantity() {
        assertThatThrownBy(() ->
                new Ingredient(BigDecimal.valueOf(-1), apple, gram)
        ).isInstanceOf(DomainException.class);
    }

    @Test
    void should_reject_null_aliment() {
        assertThatThrownBy(() ->
                new Ingredient(BigDecimal.ONE, null, gram)
        ).isInstanceOf(DomainException.class);
    }

    @Test
    void should_be_equal_when_same_values() {
        Ingredient i1 = new Ingredient(BigDecimal.ONE, apple, gram);
        Ingredient i2 = new Ingredient(BigDecimal.ONE, apple, gram);

        assertThat(i1).isEqualTo(i2);
    }



}
