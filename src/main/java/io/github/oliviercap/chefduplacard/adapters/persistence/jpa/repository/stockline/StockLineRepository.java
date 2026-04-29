package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.stockline;

import io.github.oliviercap.chefduplacard.adapters.persistence.converter.aliment.IAlimentJpaToDtoConverter;
import io.github.oliviercap.chefduplacard.adapters.persistence.converter.stockline.IStockLineJpaToDtoConverter;
import io.github.oliviercap.chefduplacard.adapters.persistence.converter.unit.IUnitJpaToDtoConverter;
import io.github.oliviercap.chefduplacard.adapters.persistence.dto.StockLineDTO;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.StockLineJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.stockline.IStockLineMapper;
import io.github.oliviercap.chefduplacard.domain.stock.StockLine;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StockLineRepository implements IStockLineRepository {
    private final IStockLineJpaRepository stockLineJpaRepository;
    private final IAlimentJpaToDtoConverter alimentJpaToDtoConverter;
    private final IUnitJpaToDtoConverter unitJpaToDtoConverter;
    private final IStockLineMapper stockLineMapper;
    private final IStockLineJpaToDtoConverter stockLineJpaToDtoConverter;

    public StockLineRepository(IStockLineJpaRepository stockLineJpaRepository,
                               IAlimentJpaToDtoConverter alimentJpaToDtoConverter,
                               IUnitJpaToDtoConverter unitJpaToDtoConverter,
                               IStockLineMapper stockLineMapper,
                               IStockLineJpaToDtoConverter stockLineJpaToDtoConverter) {
        this.stockLineJpaRepository = stockLineJpaRepository;
        this.alimentJpaToDtoConverter = alimentJpaToDtoConverter;
        this.unitJpaToDtoConverter = unitJpaToDtoConverter;
        this.stockLineMapper = stockLineMapper;
        this.stockLineJpaToDtoConverter = stockLineJpaToDtoConverter;
    }


    @Override
    public List<StockLine> findAllComplete() {
        return stockLineJpaRepository.findAllComplete().stream()
                .map(stockLineJpaToDtoConverter::toDTO)
                .map(stockLineMapper::toDomain)
                .toList();
    }

    private StockLineDTO toDTO(StockLineJpa stockLineJpa) {
        return new StockLineDTO(
                stockLineJpa.getQuantity(),
                alimentJpaToDtoConverter.toDTO(stockLineJpa.getAlimentJpa()),
                unitJpaToDtoConverter.toDTO(stockLineJpa.getUnitJpa())
        );
    }
}
