package io.github.oliviercap.chefduplacard.adapters.persistence.mapper;

import io.github.oliviercap.chefduplacard.adapters.persistence.dto.AlimentDTO;
import io.github.oliviercap.chefduplacard.adapters.persistence.dto.StockLineDTO;
import io.github.oliviercap.chefduplacard.adapters.persistence.dto.UnitDTO;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.aliment.AlimentMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.aliment.IAlimentMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.stockline.IStockLineMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.stockline.StockLineMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.unit.IUnitMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.unit.UnitMapper;
import io.github.oliviercap.chefduplacard.domain.food.Aliment;
import io.github.oliviercap.chefduplacard.domain.stock.StockLine;
import io.github.oliviercap.chefduplacard.domain.unit.Unit;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class StockLineMapperTest {

    //Test transformation stockline dto -> stockline domain
    @Test
    void creates_stockline_domain_from_dto(){
        AlimentDTO alimentDTO = new AlimentDTO("name","description", true);
        UnitDTO unitDTO = new UnitDTO("name","symbol");
        StockLineDTO stockLineDTO = new StockLineDTO(BigDecimal.valueOf(1), alimentDTO, unitDTO);

        Aliment aliment = new Aliment("name", "description",true);
        Unit unit = new Unit("name","symbol");
        StockLine stockLineExpected = new StockLine(BigDecimal.valueOf(1), aliment, unit);

        IAlimentMapper alimentMapper = new AlimentMapper();
        IUnitMapper unitMapper = new UnitMapper();

        IStockLineMapper stockLineMapper = new StockLineMapper(alimentMapper, unitMapper);

        assertThat(stockLineMapper.toDomain(stockLineDTO).getAliment().getName()).isEqualTo(stockLineExpected.getAliment().getName());
        assertThat(stockLineMapper.toDomain(stockLineDTO)).isEqualTo(stockLineExpected);
    }
}
