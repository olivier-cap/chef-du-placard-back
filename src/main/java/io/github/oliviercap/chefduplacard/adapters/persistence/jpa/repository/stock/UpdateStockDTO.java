package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.stock;

import java.math.BigDecimal;
import java.util.List;

public record UpdateStockDTO(
        Long stockId,
        List<NewQuantities> newQuantities
) {
    public record NewQuantities(
            Long stockLineId,
            BigDecimal newQuantity,
            Long unitId
    ) {}
}
