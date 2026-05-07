package io.github.oliviercap.chefduplacard.adapters.persistence.dto;

import java.util.List;

/**
 * DTO for stock entity
 * @param name
 * @param stockLineDTOs
 */
public record StockDTO(String name, List<StockLineDTO> stockLines) {}