package io.github.oliviercap.chefduplacard.application.dto;

import io.github.oliviercap.chefduplacard.domain.stock.StockLine;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * DTO for stock line jpa entity
 *
 * @param quantity
 * @param aliment
 * @param unit
 */
public record StockLineResponse(
        AlimentResponse aliment,
        UnitResponse unit,
        BigDecimal quantity
) {
    public static StockLineResponse from(StockLine stockLine) {
        Objects.requireNonNull(stockLine, "stockLine must not be null");
        Objects.requireNonNull(stockLine.getAliment(), "aliment must not be null");
        Objects.requireNonNull(stockLine.getUnit(), "unit must not be null");

        return new StockLineResponse(
                AlimentResponse.from(stockLine.getAliment()),
                UnitResponse.from(stockLine.getUnit()),
                stockLine.getQuantity()
        );
    }
}