package io.github.oliviercap.chefduplacard.application.dto;

import io.github.oliviercap.chefduplacard.domain.stock.Stock;

import java.util.List;
import java.util.Objects;

/**
 * DTO for stock entity
 * @param name
 */
public record StockResponse(String name, List<StockLineResponse> stockLines) {

    public static StockResponse from(Stock stock) {
        Objects.requireNonNull(stock, "stock must not be null");

        return new StockResponse(
                stock.getName(),
                stock.getStockMap().values().stream()
                        .map(StockLineResponse::from)
                        .toList()
        );
    }
}