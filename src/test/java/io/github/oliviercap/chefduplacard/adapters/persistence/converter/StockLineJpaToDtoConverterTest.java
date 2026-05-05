package io.github.oliviercap.chefduplacard.adapters.persistence.converter;

import io.github.oliviercap.chefduplacard.adapters.persistence.converter.aliment.AlimentJpaToDtoConverter;
import io.github.oliviercap.chefduplacard.adapters.persistence.converter.aliment.IAlimentJpaToDtoConverter;
import io.github.oliviercap.chefduplacard.adapters.persistence.converter.stockline.IStockLineJpaToDtoConverter;
import io.github.oliviercap.chefduplacard.adapters.persistence.converter.stockline.StockLineJpaToDtoConverter;
import io.github.oliviercap.chefduplacard.adapters.persistence.converter.unit.IUnitJpaToDtoConverter;
import io.github.oliviercap.chefduplacard.adapters.persistence.converter.unit.UnitJpaToDtoConverter;
import io.github.oliviercap.chefduplacard.adapters.persistence.dto.AlimentDTO;
import io.github.oliviercap.chefduplacard.adapters.persistence.dto.StockLineDTO;
import io.github.oliviercap.chefduplacard.adapters.persistence.dto.UnitDTO;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class StockLineJpaToDtoConverterTest {

    @Test
    void creates_stocklineDto_from_stokLineJpa(){
        AlimentJpa alimentJpa = new AlimentJpa("name", "description", true);
        UnitJpa unitJpa = new UnitJpa("name", "symbol");

        StockJpa stockJpa = new StockJpa("name", List.of());
        StockLineJpa stockLineJpa = new StockLineJpa(stockJpa, alimentJpa, unitJpa, BigDecimal.valueOf(1));
        stockJpa.setStockLineJpa(List.of(stockLineJpa));

        AlimentDTO alimentDTO = new AlimentDTO("name", "description", true);
        UnitDTO unitDTO = new UnitDTO("name","symbol");

        IAlimentJpaToDtoConverter alimentJpaToDtoConverter = new AlimentJpaToDtoConverter();
        IUnitJpaToDtoConverter unitJpaToDtoConverter = new UnitJpaToDtoConverter();
        IStockLineJpaToDtoConverter stockLineJpaToDtoConverter = new StockLineJpaToDtoConverter(alimentJpaToDtoConverter, unitJpaToDtoConverter);

        StockLineDTO expectedDTO = new StockLineDTO(BigDecimal.valueOf(1), alimentDTO, unitDTO);
        StockLineDTO stockLineDTO = stockLineJpaToDtoConverter.toDTO(stockLineJpa);

        assertThat(stockLineDTO).isEqualTo(expectedDTO);
    }
}
