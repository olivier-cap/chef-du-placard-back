package io.github.oliviercap.chefduplacard.application.converter.stocklineresponse;

import io.github.oliviercap.chefduplacard.application.dto.StockLineResponse;
import io.github.oliviercap.chefduplacard.domain.stock.StockLine;

public interface IStockLineToStockLineResponse {
    public StockLineResponse toDTO(StockLine stockLine);
}
