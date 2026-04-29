package io.github.oliviercap.chefduplacard.adapters.persistence.converter.stockline;

import io.github.oliviercap.chefduplacard.adapters.persistence.dto.StockLineDTO;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.StockLineJpa;

public interface IStockLineJpaToDtoConverter {
    StockLineDTO toDTO(StockLineJpa stockLineJpa);
}
