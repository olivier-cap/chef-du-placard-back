package io.github.oliviercap.chefduplacard.adapters.persistence.mapper.stockline;

import io.github.oliviercap.chefduplacard.adapters.persistence.dto.StockLineDTO;
import io.github.oliviercap.chefduplacard.domain.stock.StockLine;

public interface IStockLineMapper {
    StockLine toDomain(StockLineDTO stockLineDTO);
}
