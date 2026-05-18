package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.stock;

import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.stock.StockMapper;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IStockRepository;
import io.github.oliviercap.chefduplacard.domain.stock.Stock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Stock Repository.
 * Purpose functions to access data in database about stock.
 */
@Repository
public class StockRepository implements IStockRepository {
    private final IStockJpaRepository stockJpaRepository;
    private final StockMapper stockMapper;

    public StockRepository(IStockJpaRepository stockJpaRepository,
                           StockMapper stockMapper
                           ) {
        this.stockJpaRepository = stockJpaRepository;
        this.stockMapper = stockMapper;
    }

    /**
     * Find a particular stock by its name.
     * Creates all the chain : all stocklines, aliments et units are created
     * And linked to the stock
     * @param name name of the required stock
     * @return Stock
     */
    @Override
    public Optional<Stock> findByName(String name) {
        return stockJpaRepository.findCompleteByName(name)
                .map(stockMapper::toDomain);
    }

}
