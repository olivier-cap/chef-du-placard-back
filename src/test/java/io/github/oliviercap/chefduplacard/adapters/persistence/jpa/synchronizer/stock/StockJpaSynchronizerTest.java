package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.synchronizer.stock;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.AlimentJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.StockJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.StockLineJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UnitJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UserJpa;
import io.github.oliviercap.chefduplacard.domain.food.Aliment;
import io.github.oliviercap.chefduplacard.domain.food.AlimentId;
import io.github.oliviercap.chefduplacard.domain.stock.Stock;
import io.github.oliviercap.chefduplacard.domain.stock.StockId;
import io.github.oliviercap.chefduplacard.domain.stock.StockLine;
import io.github.oliviercap.chefduplacard.domain.stock.StockLineId;
import io.github.oliviercap.chefduplacard.domain.unit.Unit;
import io.github.oliviercap.chefduplacard.domain.unit.UnitId;
import io.github.oliviercap.chefduplacard.domain.user.User;
import io.github.oliviercap.chefduplacard.domain.user.UserId;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class StockJpaSynchronizerTest {

    private static final Long APPLE_ID = 1L;
    private static final Long GRAPEFRUIT_ID = 2L;
    private static final Long UNIT_ID = 1L;

    private static final User OWNER = new User(
            new UserId(1L),
            "test-user",
            "test-user@example.com",
            false
    );

    private final StockJpaSynchronizer stockJpaSynchronizer =
            new StockJpaSynchronizer();

    @Test
    void should_add_new_stock_line_and_update_existing_line() {
        AlimentJpa appleJpa = new AlimentJpa(
                APPLE_ID,
                "apple",
                "a",
                true
        );

        AlimentJpa grapefruitJpa = new AlimentJpa(
                GRAPEFRUIT_ID,
                "grapefruit",
                "b",
                true
        );

        UnitJpa gramJpa = new UnitJpa(
                UNIT_ID,
                "gramme",
                "g"
        );

        UserJpa ownerJpa = new UserJpa(
                1L,
                "test-user",
                "test-user@example.com",
                false
        );

        StockJpa stockJpa = new StockJpa("test", ownerJpa);
        stockJpa.addStockLine(
                new StockLineJpa(
                        appleJpa,
                        gramJpa,
                        BigDecimal.ONE
                )
        );

        Aliment apple = new Aliment(
                new AlimentId(APPLE_ID),
                "apple",
                "a",
                true
        );

        Aliment grapefruit = new Aliment(
                new AlimentId(GRAPEFRUIT_ID),
                "grapefruit",
                "b",
                true
        );

        Unit gram = new Unit(
                new UnitId(UNIT_ID),
                "gramme",
                "g"
        );

        StockLine updatedAppleLine = new StockLine(
                new StockLineId(1L),
                BigDecimal.valueOf(24),
                apple,
                gram
        );

        StockLine newGrapefruitLine = new StockLine(
                new StockLineId(2L),
                BigDecimal.valueOf(2),
                grapefruit,
                gram
        );

        Stock newStock = new Stock(
                new StockId(1L),
                "test",
                List.of(updatedAppleLine, newGrapefruitLine),
                OWNER
        );

        Map<Long, AlimentJpa> existingAliments = Map.of(
                APPLE_ID, appleJpa,
                GRAPEFRUIT_ID, grapefruitJpa
        );

        Map<Long, UnitJpa> existingUnits = Map.of(
                UNIT_ID, gramJpa
        );

        stockJpaSynchronizer.synchronize(
                stockJpa,
                newStock,
                existingAliments,
                existingUnits
        );

        assertThat(stockJpa.getStockLineJpa()).hasSize(2);

        assertThat(stockJpa.getStockLineJpa())
                .filteredOn(line ->
                        APPLE_ID.equals(line.getAlimentJpa().getId()))
                .singleElement()
                .extracting(StockLineJpa::getQuantity)
                .isEqualTo(BigDecimal.valueOf(24));

        assertThat(stockJpa.getStockLineJpa())
                .filteredOn(line ->
                        GRAPEFRUIT_ID.equals(line.getAlimentJpa().getId()))
                .singleElement()
                .extracting(StockLineJpa::getQuantity)
                .isEqualTo(BigDecimal.valueOf(2));
    }

    @Test
    void should_delete_stock_line_no_longer_present_in_new_stock() {
        AlimentJpa appleJpa = new AlimentJpa(
                APPLE_ID,
                "apple",
                "a",
                true
        );

        AlimentJpa grapefruitJpa = new AlimentJpa(
                GRAPEFRUIT_ID,
                "grapefruit",
                "b",
                true
        );

        UnitJpa gramJpa = new UnitJpa(
                UNIT_ID,
                "gramme",
                "g"
        );

        UserJpa ownerJpa = new UserJpa(
                1L,
                "test-user",
                "test-user@example.com",
                false
        );

        StockJpa stockJpa = new StockJpa("test", ownerJpa);
        stockJpa.addStockLine(
                new StockLineJpa(
                        appleJpa,
                        gramJpa,
                        BigDecimal.ONE
                )
        );
        stockJpa.addStockLine(
                new StockLineJpa(
                        grapefruitJpa,
                        gramJpa,
                        BigDecimal.TEN
                )
        );

        Aliment apple = new Aliment(
                new AlimentId(APPLE_ID),
                "apple",
                "a",
                true
        );

        Unit gram = new Unit(
                new UnitId(UNIT_ID),
                "gramme",
                "g"
        );

        StockLine updatedAppleLine = new StockLine(
                new StockLineId(1L),
                BigDecimal.valueOf(24),
                apple,
                gram
        );

        Stock newStock = new Stock(
                new StockId(1L),
                "test",
                List.of(updatedAppleLine),
                OWNER
        );

        Map<Long, AlimentJpa> existingAliments = Map.of(
                APPLE_ID, appleJpa,
                GRAPEFRUIT_ID, grapefruitJpa
        );

        Map<Long, UnitJpa> existingUnits = Map.of(
                UNIT_ID, gramJpa
        );

        stockJpaSynchronizer.synchronize(
                stockJpa,
                newStock,
                existingAliments,
                existingUnits
        );

        assertThat(stockJpa.getStockLineJpa())
                .singleElement()
                .satisfies(line -> {
                    assertThat(line.getAlimentJpa().getId())
                            .isEqualTo(APPLE_ID);
                    assertThat(line.getQuantity())
                            .isEqualByComparingTo(BigDecimal.valueOf(24));
                });
    }
}