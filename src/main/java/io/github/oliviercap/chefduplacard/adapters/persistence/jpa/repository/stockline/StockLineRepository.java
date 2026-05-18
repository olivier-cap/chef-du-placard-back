package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.stockline;

import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.stockline.StockLineMapper;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IStockLineRepository;
import io.github.oliviercap.chefduplacard.domain.stock.StockLine;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StockLineRepository implements IStockLineRepository {
    private final IStockLineJpaRepository stockLineJpaRepository;
    private final StockLineMapper stockLineMapper;

    public StockLineRepository(
            IStockLineJpaRepository stockLineJpaRepository,
            StockLineMapper stockLineMapper
    ) {
        this.stockLineJpaRepository = stockLineJpaRepository;
        this.stockLineMapper = stockLineMapper;
    }


    @Override
    public List<StockLine> findAllComplete() {
        return stockLineJpaRepository.findAllComplete().stream()
                .map(stockLineMapper::toDomain)
                .toList();
    }
}
