package io.github.oliviercap.chefduplacard.domain.stock;

import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;

public record StockLineId(Long id) {

    public StockLineId {
        if (id == null) {
            throw new DomainException(
                    "Stock line id must not be null"
            );
        }

        if (id <= 0) {
            throw new DomainException(
                    "Stock line id must be positive"
            );
        }
    }
}