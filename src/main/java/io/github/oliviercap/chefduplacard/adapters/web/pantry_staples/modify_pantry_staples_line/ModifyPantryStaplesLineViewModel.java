package io.github.oliviercap.chefduplacard.adapters.web.pantry_staples.modify_pantry_staples_line;

import java.math.BigDecimal;

public record ModifyPantryStaplesLineViewModel(
        Long pantryStrapleLineId,
        String alimentName,
        String unitCode,
        BigDecimal minimalQuantity
) {
}
