package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.synchronizer.stock;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.AlimentJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.StockJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.StockLineJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UnitJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.aliment.AlimentMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.stockline.StockLineMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.unit.UnitMapper;
import io.github.oliviercap.chefduplacard.domain.food.Aliment;
import io.github.oliviercap.chefduplacard.domain.stock.Stock;
import io.github.oliviercap.chefduplacard.domain.stock.StockLine;
import io.github.oliviercap.chefduplacard.domain.unit.Unit;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


public class StockJpaSynchronizerTest {

    private StockJpaSynchronizer stockJpaSynchronizer;
    private AlimentMapper alimentMapper = new AlimentMapper();
    private UnitMapper unitMapper = new UnitMapper();
    private StockLineMapper stockLineMapper = new StockLineMapper(alimentMapper, unitMapper);

    @Test
    public void add_new_stockLine_with_partially_existing_aliments() {
        stockJpaSynchronizer = new StockJpaSynchronizer(
                alimentMapper,
                stockLineMapper
        );

        //StockJpa doit être correctement modifié
        StockJpa stockJpa = new StockJpa("test");

        AlimentJpa alimentJpa1 = new AlimentJpa("apple","a",true);
        AlimentJpa alimentJpa2 = new AlimentJpa("grapefruit","b",true);

        UnitJpa unitJpa = new UnitJpa("gramme","g");

        StockLineJpa stockLineJpa1 = new StockLineJpa(alimentJpa1, unitJpa, BigDecimal.valueOf(1));
        stockJpa.addStockLine(stockLineJpa1);


        Aliment aliment1 = new Aliment("apple","a",true);
        Aliment aliment2 = new Aliment("grapefruit","b",true);

        Unit unit = new Unit("gramme","g");

        StockLine stockLine1 = new StockLine(BigDecimal.valueOf(24), aliment1, unit);
        StockLine stockLine2 = new StockLine(BigDecimal.valueOf(2), aliment2, unit);

        Stock stock = new Stock("test", List.of(stockLine1, stockLine2));

        Map<String, AlimentJpa> existingAliment = new HashMap<>();
        existingAliment.put(alimentJpa1.getName(), alimentJpa1);
        existingAliment.put(alimentJpa2.getName(), alimentJpa2);

        Map<String,UnitJpa> existingUnit = new HashMap<>();
        existingUnit.put(unitJpa.getName(), unitJpa);

        stockJpaSynchronizer.synchronize(
                stockJpa,
                stock,
                existingAliment,
                existingUnit
        );

        BigDecimal aliment1newQuantity = stockJpa.getStockLineJpa().getFirst().getQuantity();
        BigDecimal aliment2newQuantity = stockJpa.getStockLineJpa().getLast().getQuantity();

        assertThat(aliment1newQuantity).isEqualByComparingTo(BigDecimal.valueOf(24));
        assertThat(aliment2newQuantity).isEqualByComparingTo(BigDecimal.valueOf(2));

    }


    @Test
    public void delete_stockLine_no_more_existing_in_stock() {
        stockJpaSynchronizer = new StockJpaSynchronizer(
                alimentMapper,
                stockLineMapper
        );

        //StockJpa doit être correctement modifié
        StockJpa stockJpa = new StockJpa("test");

        AlimentJpa alimentJpa1 = new AlimentJpa("apple","a",true);
        AlimentJpa alimentJpa2 = new AlimentJpa("grapefruit","b",true);

        UnitJpa unitJpa = new UnitJpa("gramme","g");

        StockLineJpa stockLineJpa1 = new StockLineJpa(alimentJpa1, unitJpa, BigDecimal.valueOf(1));
        StockLineJpa stockLineJpa2 = new StockLineJpa(alimentJpa2, unitJpa, BigDecimal.valueOf(10));

        stockJpa.addStockLine(stockLineJpa1);
        stockJpa.addStockLine(stockLineJpa2);


        Aliment aliment1 = new Aliment("apple","a",true);
        Aliment aliment2 = new Aliment("grapefruit","b",true);

        Unit unit = new Unit("gramme","g");

        StockLine stockLine1 = new StockLine(BigDecimal.valueOf(24), aliment1, unit);

        Stock stock = new Stock("test", List.of(stockLine1));

        Map<String, AlimentJpa> existingAliment = new HashMap<>();
        existingAliment.put(alimentJpa1.getName(), alimentJpa1);
        existingAliment.put(alimentJpa2.getName(), alimentJpa2);

        Map<String,UnitJpa> existingUnit = new HashMap<>();
        existingUnit.put(unitJpa.getName(), unitJpa);

        stockJpaSynchronizer.synchronize(
                stockJpa,
                stock,
                existingAliment,
                existingUnit
        );

        BigDecimal aliment1newQuantity = stockJpa.getStockLineJpa().getFirst().getQuantity();

        assertThat(stockJpa.getStockLineJpa().size()).isEqualTo(1);
        assertThat(aliment1newQuantity).isEqualByComparingTo(BigDecimal.valueOf(24));

    }
}
