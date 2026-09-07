package io.github.oliviercap.chefduplacard.domain.pantry_staples;

import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;

public record PantryStaplesId(Long id) {
    public PantryStaplesId {
        if (id == null) {
            throw new DomainException(
                    "PantryStaples id must not be null"
            );
        }

        if (id <= 0) {
            throw new DomainException(
                    "PantryStaples id must be positive"
            );
        }
    }
}
