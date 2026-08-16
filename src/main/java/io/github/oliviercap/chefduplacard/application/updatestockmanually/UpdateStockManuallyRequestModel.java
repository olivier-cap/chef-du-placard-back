package io.github.oliviercap.chefduplacard.application.updatestockmanually;

import java.math.BigDecimal;
import java.util.List;

public record UpdateStockManuallyRequestModel(
        Long stockId,
        List<UpdateStockAliment> updateStockAliments
) {
    public record UpdateStockAliment(
            Long stockLineId,
            BigDecimal newQuantity,
            Long unitId){}
}
