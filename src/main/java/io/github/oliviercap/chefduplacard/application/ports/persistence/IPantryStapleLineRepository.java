package io.github.oliviercap.chefduplacard.application.ports.persistence;

import io.github.oliviercap.chefduplacard.domain.pantry_staples.PantryStaplesLine;

import java.math.BigDecimal;

public interface IPantryStapleLineRepository {
    PantryStaplesLine modifyPantryStaplesLine(Long pantryStaplesLineId, BigDecimal quantity, Long unitId);
    void deletePantryStaplesLine(Long pantryStaplesLineId);
}
