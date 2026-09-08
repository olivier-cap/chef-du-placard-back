package io.github.oliviercap.chefduplacard.domain.shopping_list;

import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;

public record ShoppingListLineId(Long id) {

    public ShoppingListLineId {
        if (id == null) {
            throw new DomainException(
                    "ShoppingListLineId must not be null"
            );
        }

        if (id <= 0) {
            throw new DomainException(
                    "ShoppingListLineId must be positive"
            );
        }
    }
}
