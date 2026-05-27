package io.github.oliviercap.chefduplacard.application.updatestockmanually;

import java.math.BigDecimal;
import java.util.List;

public record UpdateStockManuallyRequestModel(
        String stockName,
        List<UpdateStockAliment> updateStockAliments
) {
    public record UpdateStockAliment(
            String alimentName,
            BigDecimal newQuantity,
            String unitName){}
}
