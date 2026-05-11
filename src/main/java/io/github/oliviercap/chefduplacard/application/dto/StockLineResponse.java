package io.github.oliviercap.chefduplacard.application.dto;

import java.math.BigDecimal;

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
) {}