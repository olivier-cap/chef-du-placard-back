package io.github.oliviercap.chefduplacard.application.converter.stockresponse;

import io.github.oliviercap.chefduplacard.application.dto.StockResponse;
import io.github.oliviercap.chefduplacard.domain.stock.Stock;

public interface IStockToStockResponse {
    public StockResponse toDTO(Stock stock);
}
