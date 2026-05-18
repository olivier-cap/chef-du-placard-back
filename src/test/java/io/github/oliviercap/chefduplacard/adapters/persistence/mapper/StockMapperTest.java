package io.github.oliviercap.chefduplacard.adapters.persistence.mapper;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.AlimentJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.StockJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.StockLineJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UnitJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.aliment.AlimentMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.stock.StockMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.stockline.StockLineMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.unit.UnitMapper;
import io.github.oliviercap.chefduplacard.domain.food.Aliment;
import io.github.oliviercap.chefduplacard.domain.stock.Stock;
import io.github.oliviercap.chefduplacard.domain.stock.StockLine;
import io.github.oliviercap.chefduplacard.domain.unit.Unit;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class StockMapperTest {

    @Test
    void creates_stock_domain_from_jpa(){
        AlimentJpa alimentJpa = new AlimentJpa("name","description", true);
        UnitJpa unitJpa = new UnitJpa("name","symbol");
        StockLineJpa stockLineJpa = new StockLineJpa(alimentJpa, unitJpa, BigDecimal.valueOf(1));
        StockJpa stockJpa = new StockJpa("test", List.of(stockLineJpa));

        Aliment aliment = new Aliment("name", "description",true);
        Unit unit = new Unit("name","symbol");
        StockLine stockLine = new StockLine(BigDecimal.valueOf(1), aliment, unit);
        Stock stockExpected = new Stock("test", List.of(stockLine));

        AlimentMapper alimentMapper = new AlimentMapper();
        UnitMapper unitMapper = new UnitMapper();
        StockLineMapper stockLineMapper = new StockLineMapper(alimentMapper, unitMapper);
        StockMapper stockMapper = new StockMapper(stockLineMapper);

        Stock stock = stockMapper.toDomain(stockJpa);

        assertThat(stock.getStockMap().keySet()).isEqualTo(stockExpected.getStockMap().keySet());
        assertThat(stock).isEqualTo(stockExpected);
    }
}
