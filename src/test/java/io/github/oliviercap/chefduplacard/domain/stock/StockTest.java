package io.github.oliviercap.chefduplacard.domain.stock;

import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import io.github.oliviercap.chefduplacard.domain.food.Aliment;
import io.github.oliviercap.chefduplacard.domain.food.AlimentId;
import io.github.oliviercap.chefduplacard.domain.food.Ingredient;
import io.github.oliviercap.chefduplacard.domain.food.IngredientId;
import io.github.oliviercap.chefduplacard.domain.unit.Unit;
import io.github.oliviercap.chefduplacard.domain.unit.UnitId;
import io.github.oliviercap.chefduplacard.domain.user.User;
import io.github.oliviercap.chefduplacard.domain.user.UserId;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StockTest {

    private static final User OWNER = new User(
            new UserId(1L),
            "olivier",
            "olivier@example.com",
            false
    );

    @Test
    void stock_check_listOfIngredients_is_covered_with_same_unity() {
        Aliment apple = new Aliment(new AlimentId(1L), "apple", "fruit", true);
        Unit unit = new Unit(new UnitId(1L), "gramme", "g");
        BigDecimal quantity = BigDecimal.valueOf(12);
        BigDecimal sufficientStockQuantity = BigDecimal.valueOf(25);

        Ingredient ingredient = new Ingredient(
                new IngredientId(1L), quantity, apple, unit
        );
        StockLine sufficientStockLine = new StockLine(
                new StockLineId(1L), sufficientStockQuantity, apple, unit
        );
        Stock stock = new Stock(
                new StockId(1L), "test1", List.of(sufficientStockLine), OWNER
        );

        CoveredIngredients covered = new CoveredIngredients(true, List.of());

        assertThat(stock.covers(List.of(ingredient))).isEqualTo(covered);
    }

    @Test
    void stock_check_listOfIngredients_is_uncovered_with_same_unity() {
        Aliment apple = new Aliment(new AlimentId(1L), "apple", "fruit", true);
        Unit unit = new Unit(new UnitId(1L), "gramme", "g");
        BigDecimal quantity = BigDecimal.valueOf(12);
        BigDecimal insufficientStockQuantity = BigDecimal.valueOf(2);

        Ingredient ingredient = new Ingredient(
                new IngredientId(1L), quantity, apple, unit
        );
        StockLine insufficientStockLine = new StockLine(
                new StockLineId(1L), insufficientStockQuantity, apple, unit
        );
        Stock stock = new Stock(
                new StockId(1L), "test2", List.of(insufficientStockLine), OWNER
        );

        CoveredIngredients uncovered = new CoveredIngredients(
                false, List.of(ingredient)
        );

        assertThat(stock.covers(List.of(ingredient))).isEqualTo(uncovered);
    }

    @Test
    void name_null_or_blank_generate_error() {
        Aliment apple = new Aliment(new AlimentId(1L), "apple", "fruit", true);
        Unit unit = new Unit(new UnitId(1L), "gramme", "g");
        StockLine stockLine = new StockLine(
                new StockLineId(1L), BigDecimal.valueOf(2), apple, unit
        );

        assertThatThrownBy(() -> new Stock(
                new StockId(1L), "", List.of(stockLine), OWNER
        )).isInstanceOf(DomainException.class);

        assertThatThrownBy(() -> new Stock(
                new StockId(1L), null, List.of(stockLine), OWNER
        )).isInstanceOf(DomainException.class);
    }

    @Test
    void listStockLines_null_generate_error() {
        assertThatThrownBy(() -> new Stock(
                new StockId(1L), "name", null, OWNER
        )).isInstanceOf(DomainException.class);
    }

    @Test
    void one_stockLine_null_generate_error() {
        Aliment apple = new Aliment(new AlimentId(1L), "apple", "fruit", true);
        Unit unit = new Unit(new UnitId(1L), "gramme", "g");
        StockLine stockLine = new StockLine(
                new StockLineId(1L), BigDecimal.valueOf(2), apple, unit
        );
        List<StockLine> stockLines = new ArrayList<>();
        stockLines.add(stockLine);
        stockLines.add(null);

        assertThatThrownBy(() -> new Stock(
                new StockId(1L), "name", stockLines, OWNER
        )).isInstanceOf(DomainException.class);
    }

    @Test
    void duplicate_aliment_generate_error() {
        Aliment apple = new Aliment(new AlimentId(1L), "apple", "fruit", true);
        Unit unit = new Unit(new UnitId(1L), "gramme", "g");
        StockLine stockLine = new StockLine(
                new StockLineId(1L), BigDecimal.valueOf(2), apple, unit
        );

        assertThatThrownBy(() -> new Stock(
                new StockId(1L),
                "name",
                List.of(stockLine, stockLine),
                OWNER
        )).isInstanceOf(DomainException.class);
    }

    @Test
    void aliment_not_in_stocklines() {
        Aliment apple = new Aliment(new AlimentId(1L), "apple", "fruit", true);
        Aliment grapefruit = new Aliment(
                new AlimentId(2L), "grapefruit", "grapefruit", true
        );
        Unit unit = new Unit(new UnitId(1L), "gramme", "g");

        Ingredient appleIngredient = new Ingredient(
                new IngredientId(1L), BigDecimal.valueOf(12), apple, unit
        );
        Ingredient grapefruitIngredient = new Ingredient(
                new IngredientId(2L), BigDecimal.ONE, grapefruit, unit
        );
        StockLine appleStockLine = new StockLine(
                new StockLineId(1L), BigDecimal.valueOf(25), apple, unit
        );
        Stock stock = new Stock(
                new StockId(1L), "test1", List.of(appleStockLine), OWNER
        );

        CoveredIngredients uncovered = new CoveredIngredients(
                false, List.of(grapefruitIngredient)
        );

        assertThat(stock.covers(List.of(appleIngredient, grapefruitIngredient)))
                .isEqualTo(uncovered);
    }

    @Test
    void aliment_stock_zero() {
        Aliment apple = new Aliment(new AlimentId(1L), "apple", "fruit", true);
        Aliment grapefruit = new Aliment(
                new AlimentId(2L), "grapefruit", "grapefruit", true
        );
        Unit unit = new Unit(new UnitId(1L), "gramme", "g");

        Ingredient appleIngredient = new Ingredient(
                new IngredientId(1L), BigDecimal.valueOf(12), apple, unit
        );
        Ingredient grapefruitIngredient = new Ingredient(
                new IngredientId(2L), BigDecimal.TEN, grapefruit, unit
        );
        StockLine appleStockLine = new StockLine(
                new StockLineId(1L), BigDecimal.valueOf(25), apple, unit
        );
        StockLine grapefruitStockLine = new StockLine(
                new StockLineId(2L), BigDecimal.ZERO, grapefruit, unit
        );
        Stock stock = new Stock(
                new StockId(1L),
                "test1",
                List.of(appleStockLine, grapefruitStockLine),
                OWNER
        );

        CoveredIngredients uncovered = new CoveredIngredients(
                false, List.of(grapefruitIngredient)
        );

        assertThat(stock.covers(List.of(appleIngredient, grapefruitIngredient)))
                .isEqualTo(uncovered);
    }

    @Test
    void can_consume_aliment_in_stock() {
        Aliment apple = new Aliment(new AlimentId(1L), "apple", "fruit", true);
        Unit unit = new Unit(new UnitId(1L), "gramme", "g");
        Ingredient ingredient = new Ingredient(
                new IngredientId(1L), BigDecimal.valueOf(5), apple, unit
        );
        StockLine stockLine = new StockLine(
                new StockLineId(1L), BigDecimal.TEN, apple, unit
        );
        Stock stock = new Stock(
                new StockId(1L), "test1", List.of(stockLine), OWNER
        );

        assertThat(stock.consume(List.of(ingredient))).isTrue();
        assertThat(stock.getStockMap().get(apple).getQuantity())
                .isEqualByComparingTo(BigDecimal.valueOf(5));
    }

    @Test
    void can_aggregate_aliment_quantities_when_consume() {
        Aliment apple = new Aliment(new AlimentId(1L), "apple", "fruit", true);
        Unit unit = new Unit(new UnitId(1L), "gramme", "g");
        Ingredient firstIngredient = new Ingredient(
                new IngredientId(1L), BigDecimal.valueOf(5), apple, unit
        );
        Ingredient secondIngredient = new Ingredient(
                new IngredientId(2L), BigDecimal.valueOf(5), apple, unit
        );
        StockLine stockLine = new StockLine(
                new StockLineId(1L), BigDecimal.TEN, apple, unit
        );
        Stock stock = new Stock(
                new StockId(1L), "test1", List.of(stockLine), OWNER
        );

        assertThat(stock.consume(List.of(firstIngredient, secondIngredient)))
                .isTrue();
        assertThat(stock.getStockMap().get(apple).getQuantity())
                .isEqualByComparingTo(BigDecimal.ZERO);
    }
}