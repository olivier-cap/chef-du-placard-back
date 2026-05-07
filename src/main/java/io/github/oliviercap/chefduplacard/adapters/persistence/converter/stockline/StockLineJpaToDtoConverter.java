package io.github.oliviercap.chefduplacard.adapters.persistence.converter.stockline;

import io.github.oliviercap.chefduplacard.adapters.persistence.converter.aliment.IAlimentJpaToDtoConverter;
import io.github.oliviercap.chefduplacard.adapters.persistence.converter.unit.IUnitJpaToDtoConverter;
import io.github.oliviercap.chefduplacard.adapters.persistence.dto.StockLineDTO;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.StockLineJpa;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Helper to transform a StockLine JPA Entity into DTO
 */
@Component
public class StockLineJpaToDtoConverter implements IStockLineJpaToDtoConverter{
    private final IAlimentJpaToDtoConverter alimentJpaToDtoConverter;
    private final IUnitJpaToDtoConverter unitJpaToDtoConverter;

    public StockLineJpaToDtoConverter(IAlimentJpaToDtoConverter alimentJpaToDtoConverter,
                                      IUnitJpaToDtoConverter unitJpaToDtoConverter) {
        this.alimentJpaToDtoConverter = alimentJpaToDtoConverter;
        this.unitJpaToDtoConverter = unitJpaToDtoConverter;
    }

    @Override
    public StockLineDTO toDTO(StockLineJpa stockLineJpa) {
        Objects.requireNonNull(stockLineJpa, "stockLineJpa must not be null");
        Objects.requireNonNull(stockLineJpa.getAlimentJpa(), "alimentJpa must not be null");
        Objects.requireNonNull(stockLineJpa.getUnitJpa(), "unitJpa must not be null");

        return new StockLineDTO(
                alimentJpaToDtoConverter.toDTO(stockLineJpa.getAlimentJpa()),
                unitJpaToDtoConverter.toDTO(stockLineJpa.getUnitJpa()),
                stockLineJpa.getQuantity()
        );
    }
}
