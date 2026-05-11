package io.github.oliviercap.chefduplacard.application.converter.stockresponse;

import io.github.oliviercap.chefduplacard.application.converter.stocklineresponse.IStockLineToStockLineResponse;
import io.github.oliviercap.chefduplacard.application.dto.StockResponse;
import io.github.oliviercap.chefduplacard.domain.stock.Stock;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class StockToStockResponse implements IStockToStockResponse{

    private final IStockLineToStockLineResponse stockLineToStockLineResponse;

    public StockToStockResponse(IStockLineToStockLineResponse stockLineToStockLineResponse) {
        this.stockLineToStockLineResponse = stockLineToStockLineResponse;
    }

    public StockResponse toDTO(Stock stock) {
        Objects.requireNonNull(stock, "stock must not be null");

        return new StockResponse(
                stock.getName(),
                stock.getStockMap().values().stream()
                        .map(stockLineToStockLineResponse::toDTO)
                        .toList()
        );
    }
}
