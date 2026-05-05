package io.github.oliviercap.chefduplacard.domain.stock;

import io.github.oliviercap.chefduplacard.domain.food.Aliment;
import io.github.oliviercap.chefduplacard.domain.food.Ingredient;
import io.github.oliviercap.chefduplacard.domain.unit.Unit;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class StockTest {

    @Test
    void stock_check_listOfIngredients_is_covered_with_same_unity() {

        //Given
        Aliment apple = new Aliment("apple", "fruit", true);
        Unit unit = new Unit("gramme", "g");
        BigDecimal quantity = BigDecimal.valueOf(12);
        BigDecimal quantityStockSufficient = BigDecimal.valueOf(25);

        Ingredient ingredient = new Ingredient(quantity, apple, unit);

        //sufficient quantity
        //When
        StockLine stockLineSufficient = new StockLine(quantityStockSufficient, apple, unit);
        Stock stock1 = new Stock("test1", List.of(stockLineSufficient));

        //Then
        CoveredIngredients covered = new CoveredIngredients(true, List.of());
        assertThat(stock1.covers(List.of(ingredient))).isEqualTo(covered);

    }


    @Test
    void stock_check_listOfIngredients_is_uncovered_with_same_unity() {

        //Given
        Aliment apple = new Aliment("apple", "fruit", true);
        Unit unit = new Unit("gramme", "g");
        BigDecimal quantity = BigDecimal.valueOf(12);
        BigDecimal quantityStockInsufficient = BigDecimal.valueOf(2);


        Ingredient ingredient = new Ingredient(quantity, apple, unit);

        //Insufficient quantity
        //When
        StockLine stockLineInsufficient = new StockLine(quantityStockInsufficient, apple, unit);
        Stock stock2 = new Stock("test2", List.of(stockLineInsufficient));

        //Then
        CoveredIngredients uncovered = new CoveredIngredients(false, List.of(ingredient));
        assertThat(stock2.covers(List.of(ingredient))).isEqualTo(uncovered);


    }
}
