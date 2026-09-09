package io.github.oliviercap.chefduplacard.adapters.web.pantry_staples.get_pantry_staples_state;

import java.math.BigDecimal;
import java.util.List;

public record GetPantryStaplesStateViewModel(
        List<PantryStapleStateLine> lines
) {
    public record PantryStapleStateLine(
            Long pantryStrapleLineId,
            String alimentName,
            String unitCode,
            BigDecimal minimalQuantity,
            BigDecimal actualQuantity,
            boolean isSufficient
    ){}
}
