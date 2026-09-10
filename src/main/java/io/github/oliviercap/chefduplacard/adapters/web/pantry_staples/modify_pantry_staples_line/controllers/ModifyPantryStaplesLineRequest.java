package io.github.oliviercap.chefduplacard.adapters.web.pantry_staples.modify_pantry_staples_line.controllers;

import java.math.BigDecimal;

public record ModifyPantryStaplesLineRequest(
        Long pantryStapleLineId,
        BigDecimal quantity,
        Long unitId
) {
}
