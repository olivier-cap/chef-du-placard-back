package io.github.oliviercap.chefduplacard.application.htttpresponse;

import io.github.oliviercap.chefduplacard.domain.pantry_staples.PantryStaplesLine;

import java.math.BigDecimal;

public record PantryStaplesLineResponse(
        Long id,
        AlimentResponse alimentResponse,
        UnitResponse unitResponse,
        BigDecimal quantity
) {
    public static PantryStaplesLineResponse from(PantryStaplesLine pantryStaplesLine) {
        return new PantryStaplesLineResponse(
                pantryStaplesLine.getId().id(),
                AlimentResponse.from(pantryStaplesLine.getAliment()),
                UnitResponse.from(pantryStaplesLine.getUnit()),
                pantryStaplesLine.getQuantity()
        );
    }
}
