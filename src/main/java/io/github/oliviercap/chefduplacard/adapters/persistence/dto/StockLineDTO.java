package io.github.oliviercap.chefduplacard.adapters.persistence.dto;

import java.math.BigDecimal;

/**
 * DTO for stock line jpa entity
 *
 * @param quantity
 * @param aliment
 * @param unit
 */
public record StockLineDTO(
        AlimentDTO aliment,
        UnitDTO unit,
        BigDecimal quantity
) {}