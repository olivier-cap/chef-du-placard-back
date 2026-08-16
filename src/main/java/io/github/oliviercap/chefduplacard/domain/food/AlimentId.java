package io.github.oliviercap.chefduplacard.domain.food;

import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;

public record AlimentId(Long id) {

    public AlimentId {
        if (id == null) {
            throw new DomainException(
                    "Aliment id must not be null"
            );
        }

        if (id <= 0) {
            throw new DomainException(
                    "Aliment id must be positive"
            );
        }
    }
}