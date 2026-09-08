package io.github.oliviercap.chefduplacard.domain.shopping_list;

import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;

public record ShoppingListId(Long id) {

    public ShoppingListId {
        if (id == null) {
            throw new DomainException(
                    "ShoppingListId must not be null"
            );
        }

        if (id <= 0) {
            throw new DomainException(
                    "ShoppingListId must be positive"
            );
        }
    }
}
