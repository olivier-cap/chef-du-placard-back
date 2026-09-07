package io.github.oliviercap.chefduplacard.domain.aliment_type;

import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;

public record AlimentTypeId(Long id) {

    public AlimentTypeId {
        if (id == null) {
            throw new DomainException(
                    "AlimentType id must not be null"
            );
        }

        if (id <= 0) {
            throw new DomainException(
                    "AlimentType id must be positive"
            );
        }
    }
}
