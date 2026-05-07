package io.github.oliviercap.chefduplacard.adapters.persistence.mapper.stock;

import io.github.oliviercap.chefduplacard.adapters.persistence.dto.StockDTO;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.stockline.IStockLineMapper;
import io.github.oliviercap.chefduplacard.domain.stock.Stock;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class StockMapper implements IStockMapper{
    private final IStockLineMapper stockLineMapper;

    public StockMapper(IStockLineMapper stockLineMapper) {
        this.stockLineMapper = stockLineMapper;
    }

    @Override
    public Stock toDomain(StockDTO stockDTO) {
        Objects.requireNonNull(stockDTO, "stockDTO must not be null");
        Objects.requireNonNull(stockDTO.stockLines(), "stockLinesDTO must not be null");

        return new Stock(
                stockDTO.name(),
                stockDTO.stockLines().stream()
                        .map(stockLineMapper::toDomain)
                        .toList()
        );
    }
}
