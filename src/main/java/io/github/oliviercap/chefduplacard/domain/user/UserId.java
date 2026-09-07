package io.github.oliviercap.chefduplacard.domain.user;

import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;

public record UserId(Long id) {

    public UserId {
        if (id == null) {
            throw new DomainException("User id must not be null");
        }

        if (id <= 0) {
            throw new DomainException("User id must be positive");
        }
    }
}