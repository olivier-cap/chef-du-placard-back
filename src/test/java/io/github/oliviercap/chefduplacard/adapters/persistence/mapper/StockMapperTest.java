package io.github.oliviercap.chefduplacard.adapters.persistence.mapper;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.AlimentJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.StockJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.StockLineJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UnitJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.aliment.AlimentMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.stock.StockMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.stockline.StockLineMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.unit.UnitMapper;
import io.github.oliviercap.chefduplacard.domain.stock.Stock;
import io.github.oliviercap.chefduplacard.domain.stock.StockLine;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StockMapperTest {

    @Test
    void creates_stock_domain_from_jpa() {
        AlimentJpa alimentJpa = new AlimentJpa(
                1L,
                "aliment name",
                "aliment description",
                true
        );

        UnitJpa unitJpa = new UnitJpa(
                2L,
                "unit name",
                "symbol"
        );

        StockLineJpa stockLineJpa = new StockLineJpa(
                3L,
                alimentJpa,
                unitJpa,
                BigDecimal.ONE
        );

        StockJpa stockJpa = new StockJpa(
                4L,
                "test stock",
                List.of(stockLineJpa)
        );

        AlimentMapper alimentMapper = new AlimentMapper();
        UnitMapper unitMapper = new UnitMapper();

        StockLineMapper stockLineMapper = new StockLineMapper(
                alimentMapper,
                unitMapper
        );

        StockMapper stockMapper = new StockMapper(
                stockLineMapper
        );

        Stock result = stockMapper.toDomain(stockJpa);

        assertThat(result.getId().id())
                .isEqualTo(4L);

        assertThat(result.getName())
                .isEqualTo("test stock");

        assertThat(result.getStockMap())
                .hasSize(1);

        StockLine mappedStockLine = result
                .getStockMap()
                .values()
                .iterator()
                .next();

        assertThat(mappedStockLine.getId().id())
                .isEqualTo(3L);

        assertThat(mappedStockLine.getQuantity())
                .isEqualByComparingTo(BigDecimal.ONE);

        assertThat(mappedStockLine.getAliment().getId().id())
                .isEqualTo(1L);

        assertThat(mappedStockLine.getAliment().getName())
                .isEqualTo("aliment name");

        assertThat(mappedStockLine.getAliment().getDescription())
                .isEqualTo("aliment description");

        assertThat(mappedStockLine.getAliment().isActive())
                .isTrue();

        assertThat(mappedStockLine.getUnit().getId().id())
                .isEqualTo(2L);

        assertThat(mappedStockLine.getUnit().getName())
                .isEqualTo("unit name");

        assertThat(mappedStockLine.getUnit().getSymbol())
                .isEqualTo("symbol");
    }
}