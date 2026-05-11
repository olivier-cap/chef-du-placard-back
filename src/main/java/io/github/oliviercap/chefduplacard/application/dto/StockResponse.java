package io.github.oliviercap.chefduplacard.application.dto;

import java.util.List;

/**
 * DTO for stock entity
 * @param name
 * @param stockLineDTOs
 */
public record StockResponse(String name, List<StockLineResponse> stockLines) {}