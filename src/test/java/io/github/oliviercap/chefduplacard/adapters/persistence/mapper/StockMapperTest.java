package io.github.oliviercap.chefduplacard.adapters.persistence.mapper;

import io.github.oliviercap.chefduplacard.adapters.persistence.dto.*;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.aliment.AlimentMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.aliment.IAlimentMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.ingredient.IIngredientMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.ingredient.IngredientMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.stock.IStockMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.stock.StockMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.stockline.IStockLineMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.stockline.StockLineMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.unit.IUnitMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.unit.UnitMapper;
import io.github.oliviercap.chefduplacard.domain.food.Aliment;
import io.github.oliviercap.chefduplacard.domain.food.Ingredient;
import io.github.oliviercap.chefduplacard.domain.stock.Stock;
import io.github.oliviercap.chefduplacard.domain.stock.StockLine;
import io.github.oliviercap.chefduplacard.domain.unit.Unit;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class StockMapperTest {

    @Test
    void creates_stock_domain_from_dto(){
        AlimentDTO alimentDTO = new AlimentDTO("name","description", true);
        UnitDTO unitDTO = new UnitDTO("name","symbol");
        StockLineDTO stockLineDTO = new StockLineDTO(alimentDTO, unitDTO, BigDecimal.valueOf(1));
        StockDTO stockDTO = new StockDTO("test", List.of(stockLineDTO));

        Aliment aliment = new Aliment("name", "description",true);
        Unit unit = new Unit("name","symbol");
        StockLine stockLine = new StockLine(BigDecimal.valueOf(1), aliment, unit);
        Stock stockExpected = new Stock("test", List.of(stockLine));

        IAlimentMapper alimentMapper = new AlimentMapper();
        IUnitMapper unitMapper = new UnitMapper();
        IStockLineMapper stockLineMapper = new StockLineMapper(alimentMapper, unitMapper);
        IStockMapper stockMapper = new StockMapper(stockLineMapper);

        Stock stock = stockMapper.toDomain(stockDTO);

        assertThat(stock.getStockMap().keySet()).isEqualTo(stockExpected.getStockMap().keySet());
        assertThat(stock).isEqualTo(stockExpected);

    }
}
