package io.github.oliviercap.chefduplacard.adapters.web.pantry_staples.create_pantry_staples_line.controllers;

import java.math.BigDecimal;

public record CreatePantryStaplesLineRequest(Long pantryStaplesId, Long alimentId, BigDecimal quantity, Long unitId) {
}
