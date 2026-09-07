package io.github.oliviercap.chefduplacard.domain.pantry_staples;

import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;

public record PantryStaplesLineId(Long id) {
    public PantryStaplesLineId {
        if (id == null) {
            throw new DomainException(
                    "PantryStaples Line id must not be null"
            );
        }

        if (id <= 0) {
            throw new DomainException(
                    "PantryStaples Line id must be positive"
            );
        }
    }
}
