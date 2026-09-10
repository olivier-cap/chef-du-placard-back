package io.github.oliviercap.chefduplacard.application.pantry_staples.modify_pantry_staples_line;

import java.math.BigDecimal;

public record ModifyPantryStaplesLineRequestModel(Long pantryStapleLineId, BigDecimal quantity, Long unitId) {
}
