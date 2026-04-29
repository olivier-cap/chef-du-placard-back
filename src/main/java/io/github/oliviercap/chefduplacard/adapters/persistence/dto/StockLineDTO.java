package io.github.oliviercap.chefduplacard.adapters.persistence.dto;

import java.math.BigDecimal;

/**
 * DTO for stock line jpa entity
 * @param quantity
 * @param alimentDTO
 * @param unitDTO
 */
public record StockLineDTO(BigDecimal quantity, AlimentDTO alimentDTO, UnitDTO unitDTO) {
}
