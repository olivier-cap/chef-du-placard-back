package io.github.oliviercap.chefduplacard.application.htttpresponse;

import io.github.oliviercap.chefduplacard.domain.stock.Stock;

import java.util.List;
import java.util.Objects;

/**
 * DTO for stock entity
 * @param name
 */
public record StockResponse(Long id, String name, List<StockLineResponse> stockLines) {

    public static StockResponse from(Stock stock) {
        Objects.requireNonNull(stock, "stock must not be null");

        return new StockResponse(
                stock.getId().id(),
                stock.getName(),
                stock.getStockMap().values().stream()
                        .map(StockLineResponse::from)
                        .toList()
        );
    }
}