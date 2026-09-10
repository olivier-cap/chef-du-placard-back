package io.github.oliviercap.chefduplacard.application.pantry_staples.create_pantry_staples_line;

import java.math.BigDecimal;

public record CreatePantryStaplesLineRequestModel(Long pantryStaplesId, Long alimentId, BigDecimal quantity, Long unitId) {
}
