package io.github.oliviercap.chefduplacard.domain.recipe;

import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;

public record RecipeId(Long id) {

    public RecipeId {
        if (id == null) {
            throw new DomainException(
                    "Recipe id must not be null"
            );
        }

        if (id <= 0) {
            throw new DomainException(
                    "Recipe id must be positive"
            );
        }
    }
}