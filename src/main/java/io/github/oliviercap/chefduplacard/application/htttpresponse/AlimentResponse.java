package io.github.oliviercap.chefduplacard.application.htttpresponse;

import io.github.oliviercap.chefduplacard.domain.food.Aliment;

import java.util.Objects;

public record AlimentResponse(String name, String description, boolean active) {

    public static AlimentResponse from(Aliment aliment) {
        Objects.requireNonNull(aliment, "aliment must not be null");

        return new AlimentResponse(
                aliment.getName(),
                aliment.getDescription(),
                aliment.isActive()
        );
    }
}
