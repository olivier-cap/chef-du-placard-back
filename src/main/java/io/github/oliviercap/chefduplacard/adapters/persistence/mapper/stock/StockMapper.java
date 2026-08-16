package io.github.oliviercap.chefduplacard.adapters.persistence.mapper.stock;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.StockJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.stockline.StockLineMapper;
import io.github.oliviercap.chefduplacard.domain.stock.Stock;
import io.github.oliviercap.chefduplacard.domain.stock.StockId;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class StockMapper {
    private final StockLineMapper stockLineMapper;

    public StockMapper(StockLineMapper stockLineMapper) {
        this.stockLineMapper = stockLineMapper;
    }

    public Stock toDomain(StockJpa stockJpa) {
        Objects.requireNonNull(stockJpa, "stockJPA must not be null");

        return new Stock(
                new StockId(stockJpa.getId()),
                stockJpa.getName(),
                stockJpa.getStockLineJpa().stream()
                        .map(stockLineMapper::toDomain)
                        .toList()
        );
    }
}
