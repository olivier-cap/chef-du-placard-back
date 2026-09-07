package io.github.oliviercap.chefduplacard.adapters.persistence.mapper.stock;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.StockJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.stockline.StockLineMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.user.UserMapper;
import io.github.oliviercap.chefduplacard.domain.stock.Stock;
import io.github.oliviercap.chefduplacard.domain.stock.StockId;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class StockMapper {
    private final StockLineMapper stockLineMapper;
    private final UserMapper userMapper;

    public StockMapper(StockLineMapper stockLineMapper, UserMapper userMapper) {
        this.stockLineMapper = stockLineMapper;
        this.userMapper = userMapper;
    }

    public Stock toDomain(StockJpa stockJpa) {
        Objects.requireNonNull(stockJpa, "stockJPA must not be null");

        return new Stock(
                new StockId(stockJpa.getId()),
                stockJpa.getName(),
                stockJpa.getStockLineJpa().stream()
                        .map(stockLineMapper::toDomain)
                        .toList(),
                userMapper.toDomain(stockJpa.getUserJpa())
        );
    }
}
