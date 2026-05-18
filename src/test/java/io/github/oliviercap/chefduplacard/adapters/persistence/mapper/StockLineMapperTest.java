package io.github.oliviercap.chefduplacard.adapters.persistence.mapper;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.AlimentJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.StockLineJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UnitJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.aliment.AlimentMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.stockline.StockLineMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.unit.UnitMapper;
import io.github.oliviercap.chefduplacard.domain.food.Aliment;
import io.github.oliviercap.chefduplacard.domain.stock.StockLine;
import io.github.oliviercap.chefduplacard.domain.unit.Unit;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class StockLineMapperTest {

    //Test transformation stockline jpa -> stockline domain
    @Test
    void creates_stockline_domain_from_jpa(){
        AlimentJpa alimentJpa = new AlimentJpa("name","description", true);
        UnitJpa unitJpa = new UnitJpa("name","symbol");
        StockLineJpa stockLineJpa = new StockLineJpa(alimentJpa, unitJpa, BigDecimal.valueOf(1));

        Aliment aliment = new Aliment("name", "description",true);
        Unit unit = new Unit("name","symbol");
        StockLine stockLineExpected = new StockLine(BigDecimal.valueOf(1), aliment, unit);

        AlimentMapper alimentMapper = new AlimentMapper();
        UnitMapper unitMapper = new UnitMapper();

        StockLineMapper stockLineMapper = new StockLineMapper(alimentMapper, unitMapper);

        assertThat(stockLineMapper.toDomain(stockLineJpa).getAliment().getName()).isEqualTo(stockLineExpected.getAliment().getName());
        assertThat(stockLineMapper.toDomain(stockLineJpa)).isEqualTo(stockLineExpected);
    }
}
