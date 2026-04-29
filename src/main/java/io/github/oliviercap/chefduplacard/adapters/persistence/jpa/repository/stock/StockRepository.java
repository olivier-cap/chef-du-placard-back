package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.stock;

import io.github.oliviercap.chefduplacard.adapters.persistence.converter.stockline.IStockLineJpaToDtoConverter;
import io.github.oliviercap.chefduplacard.adapters.persistence.dto.StockDTO;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.StockJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.stock.IStockMapper;
import io.github.oliviercap.chefduplacard.domain.stock.Stock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Stock Repository.
 * Purpose functions to access data in database about stock.
 */
@Repository
public class StockRepository implements IStockRepository{
    private final IStockJpaRepository stockJpaRepository;
    private final IStockMapper stockMapper;
    private final IStockLineJpaToDtoConverter stockLineJpaToDtoConverter;

    public StockRepository(IStockJpaRepository stockJpaRepository,
                           IStockMapper stockMapper,
                           IStockLineJpaToDtoConverter stockLineJpaToDtoConverter) {
        this.stockJpaRepository = stockJpaRepository;
        this.stockMapper = stockMapper;
        this.stockLineJpaToDtoConverter = stockLineJpaToDtoConverter;
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
                .map(this::toDTO)
                .map(stockMapper::toDomain);
    }

    private StockDTO toDTO(StockJpa stockJpa) {
        return new StockDTO(
                stockJpa.getName(),
                stockJpa.getStockLineJpa().stream()
                        .map(stockLineJpaToDtoConverter::toDTO)
                        .toList()
        );
    }
}
