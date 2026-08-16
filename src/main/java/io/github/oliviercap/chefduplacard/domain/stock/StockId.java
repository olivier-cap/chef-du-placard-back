package io.github.oliviercap.chefduplacard.domain.stock;

import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;

public record StockId(Long id) {

    public StockId {
        if (id == null) {
            throw new DomainException(
                    "Stock id must not be null"
            );
        }

        if (id <= 0) {
            throw new DomainException(
                    "Stock id must be positive"
            );
        }
    }
}