package io.github.oliviercap.chefduplacard.application.dto;

import io.github.oliviercap.chefduplacard.domain.unit.Unit;

import java.util.Objects;

public record UnitResponse(String name, String symbol) {

    public static UnitResponse from(Unit unit) {
        Objects.requireNonNull(unit, "unit must not be null");

        return new UnitResponse(
                unit.getName(),
                unit.getSymbol()
        );
    }
}
