package io.github.oliviercap.chefduplacard.adapters.web.getstock;

import java.math.BigDecimal;
import java.util.List;

public record GetStockViewModel(
        List<StockLineViewModel> stockLineViewModelList
) {
    public record StockLineViewModel(
            Long id,
            BigDecimal quantity,
            String alimentName,
            String unitSymbol
    ) {
    }
}
