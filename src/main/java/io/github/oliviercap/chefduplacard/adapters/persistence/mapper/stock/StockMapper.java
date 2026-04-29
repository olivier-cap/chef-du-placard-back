package io.github.oliviercap.chefduplacard.adapters.persistence.mapper.stock;

import io.github.oliviercap.chefduplacard.adapters.persistence.dto.StockDTO;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.stockline.IStockLineMapper;
import io.github.oliviercap.chefduplacard.domain.stock.Stock;
import org.springframework.stereotype.Component;

@Component
public class StockMapper implements IStockMapper{
    private final IStockLineMapper stockLineMapper;

    public StockMapper(IStockLineMapper stockLineMapper) {
        this.stockLineMapper = stockLineMapper;
    }

    @Override
    public Stock toDomain(StockDTO stockDTO) {
        return new Stock(
                stockDTO.name(),
                stockDTO.stockLineDTOs().stream()
                        .map(stockLineMapper::toDomain)
                        .toList()
        );
    }
}
