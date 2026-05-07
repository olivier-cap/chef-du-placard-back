package io.github.oliviercap.chefduplacard.adapters.persistence.mapper.stockline;

import io.github.oliviercap.chefduplacard.adapters.persistence.dto.StockLineDTO;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.aliment.IAlimentMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.unit.IUnitMapper;
import io.github.oliviercap.chefduplacard.domain.stock.StockLine;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class StockLineMapper implements IStockLineMapper{
    private final IAlimentMapper alimentMapper;
    private final IUnitMapper unitMapper;

    public StockLineMapper(IAlimentMapper alimentMapper, IUnitMapper unitMapper) {
        this.alimentMapper = alimentMapper;
        this.unitMapper = unitMapper;
    }

    @Override
    public StockLine toDomain(StockLineDTO stockLineDTO) {
        Objects.requireNonNull(stockLineDTO, "stockLineDTO must not be null");
        Objects.requireNonNull(stockLineDTO.aliment(), "alimentDTO must not be null");
        Objects.requireNonNull(stockLineDTO.unit(), "unitDTO must not be null");

        return new StockLine(
                stockLineDTO.quantity(),
                alimentMapper.toDomain(stockLineDTO.aliment()),
                unitMapper.toDomain(stockLineDTO.unit())
        );
    }
}
