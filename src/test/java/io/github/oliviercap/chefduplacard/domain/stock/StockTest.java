package io.github.oliviercap.chefduplacard.domain.stock;

import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import io.github.oliviercap.chefduplacard.domain.food.Aliment;
import io.github.oliviercap.chefduplacard.domain.food.Ingredient;
import io.github.oliviercap.chefduplacard.domain.unit.Unit;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @Test
    void name_null_or_blank_generate_error() {

        //Given
        Aliment apple = new Aliment("apple", "fruit", true);
        Unit unit = new Unit("gramme", "g");
        BigDecimal quantity = BigDecimal.valueOf(12);
        BigDecimal quantityStockInsufficient = BigDecimal.valueOf(2);


        Ingredient ingredient = new Ingredient(quantity, apple, unit);
        StockLine stockLine = new StockLine(quantityStockInsufficient, apple, unit);

        //then
        assertThatThrownBy(() ->
                new Stock("", List.of(stockLine))
        ).isInstanceOf(DomainException.class);

        assertThatThrownBy(() ->
                new Stock(null, List.of(stockLine))
        ).isInstanceOf(DomainException.class);
    }

    @Test
    void listStockLines_null_generate_error() {
        //then
        assertThatThrownBy(() ->
                new Stock("name", null)
        ).isInstanceOf(DomainException.class);

    }

    @Test
    void one_stockLine_null_generate_error() {

        //Given
        Aliment apple = new Aliment("apple", "fruit", true);
        Unit unit = new Unit("gramme", "g");
        BigDecimal quantity = BigDecimal.valueOf(12);
        BigDecimal quantityStockInsufficient = BigDecimal.valueOf(2);


        Ingredient ingredient = new Ingredient(quantity, apple, unit);
        StockLine stockLine = new StockLine(quantityStockInsufficient, apple, unit);
        List<StockLine> list = new ArrayList<>();
        list.add(stockLine);

        //With
        list.add(null);

        //then
        assertThatThrownBy(() ->
                new Stock("name", list)
        ).isInstanceOf(DomainException.class);

    }

    @Test
    void duplicate_aliment_generate_error() {

        //Given
        Aliment apple = new Aliment("apple", "fruit", true);
        Unit unit = new Unit("gramme", "g");
        BigDecimal quantity = BigDecimal.valueOf(12);
        BigDecimal quantityStockInsufficient = BigDecimal.valueOf(2);


        Ingredient ingredient = new Ingredient(quantity, apple, unit);
        StockLine stockLine = new StockLine(quantityStockInsufficient, apple, unit);

        //then
        assertThatThrownBy(() ->
                new Stock("name", List.of(stockLine, stockLine))
        ).isInstanceOf(DomainException.class);
    }

    @Test
    void aliment_not_in_stocklines() {
        //Given
        Aliment apple = new Aliment("apple", "fruit", true);
        Aliment grapefruit = new Aliment("grapefruit", "grapefruit", true);

        Unit unit = new Unit("gramme", "g");
        BigDecimal quantity = BigDecimal.valueOf(12);
        BigDecimal quantityStockSufficient = BigDecimal.valueOf(25);

        Ingredient ingredient = new Ingredient(quantity, apple, unit);
        Ingredient grapefruitIngredient = new Ingredient(BigDecimal.valueOf(1), grapefruit, unit);


        //sufficient quantity
        //When
        StockLine stockLineapple = new StockLine(quantityStockSufficient, apple, unit);

        Stock stock1 = new Stock("test1", List.of(stockLineapple));

        //Then
        CoveredIngredients covered = new CoveredIngredients(false, List.of(grapefruitIngredient));
        assertThat(stock1.covers(List.of(ingredient, grapefruitIngredient))).isEqualTo(covered);
    }

    @Test
    void aliment_stock_zero() {
        //Given
        Aliment apple = new Aliment("apple", "fruit", true);
        Aliment grapefruit = new Aliment("grapefruit", "grapefruit", true);

        Unit unit = new Unit("gramme", "g");
        BigDecimal quantity = BigDecimal.valueOf(12);
        BigDecimal quantityStockSufficient = BigDecimal.valueOf(25);

        Ingredient ingredient = new Ingredient(quantity, apple, unit);
        Ingredient grapefruitIngredient = new Ingredient(BigDecimal.valueOf(10), grapefruit, unit);


        //sufficient quantity
        //When
        StockLine stockLineapple = new StockLine(quantityStockSufficient, apple, unit);
        StockLine stockLinegrapefruit = new StockLine(BigDecimal.valueOf(0), grapefruit, unit);

        Stock stock1 = new Stock("test1", List.of(stockLineapple, stockLinegrapefruit));

        //Then
        CoveredIngredients covered = new CoveredIngredients(false, List.of(grapefruitIngredient));
        assertThat(stock1.covers(List.of(ingredient, grapefruitIngredient))).isEqualTo(covered);
    }

    @Test
    void can_consume_aliment_in_stock() {
        //Given
        Aliment apple = new Aliment("apple", "fruit", true);
        Unit unit = new Unit("gramme", "g");

        Ingredient ingredient = new Ingredient(BigDecimal.valueOf(5), apple, unit);
        StockLine stockLine = new StockLine(BigDecimal.valueOf(10), apple, unit);

        Stock stock1 = new Stock("test1", List.of(stockLine));

        assertThat(stock1.consume(List.of(ingredient))).isTrue();
        assertThat(stock1.getStockMap().get(apple).getQuantity()).isEqualByComparingTo(BigDecimal.valueOf(5));
    }

    @Test
    void can_aggregate_aliment_quantities_when_consume() {
        Aliment apple = new Aliment("apple", "fruit", true);
        Unit unit = new Unit("gramme", "g");

        Ingredient ingredient = new Ingredient(BigDecimal.valueOf(5), apple, unit);
        Ingredient ingredient2 = new Ingredient(BigDecimal.valueOf(5), apple, unit);

        StockLine stockLine = new StockLine(BigDecimal.valueOf(10), apple, unit);

        Stock stock1 = new Stock("test1", List.of(stockLine));

        assertThat(stock1.consume(List.of(ingredient, ingredient2))).isTrue();
        assertThat(stock1.getStockMap().get(apple).getQuantity()).isEqualByComparingTo(BigDecimal.valueOf(0));
    }
}
