package io.github.oliviercap.chefduplacard.adapters.persistence.mapper.stockline;

import io.github.oliviercap.chefduplacard.adapters.persistence.dto.StockLineDTO;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.aliment.IAlimentMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.unit.IUnitMapper;
import io.github.oliviercap.chefduplacard.domain.stock.StockLine;
import org.springframework.stereotype.Component;

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
        return new StockLine(
                stockLineDTO.quantity(),
                alimentMapper.toDomain(stockLineDTO.alimentDTO()),
                unitMapper.toDomain(stockLineDTO.unitDTO())
        );
    }
}
