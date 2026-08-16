package io.github.oliviercap.chefduplacard.domain.food;

import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;

public record IngredientId(Long id) {

    public IngredientId {
        if (id == null) {
            throw new DomainException(
                    "Ingredient id must not be null"
            );
        }

        if (id <= 0) {
            throw new DomainException(
                    "Ingredient id must be positive"
            );
        }
    }
}