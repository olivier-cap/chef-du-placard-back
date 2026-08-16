package io.github.oliviercap.chefduplacard.domain.unit;

import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;

public record UnitId(Long id) {

    public UnitId {
        if (id == null) {
            throw new DomainException("Unit id must not be null");
        }

        if (id <= 0) {
            throw new DomainException("Unit id must be positive");
        }
    }
}