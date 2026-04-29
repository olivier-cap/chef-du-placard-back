package io.github.oliviercap.chefduplacard.adapters.persistence.mapper.stock;

import io.github.oliviercap.chefduplacard.adapters.persistence.dto.StockDTO;
import io.github.oliviercap.chefduplacard.domain.stock.Stock;

public interface IStockMapper {
    Stock toDomain(StockDTO stockDTO);
}
