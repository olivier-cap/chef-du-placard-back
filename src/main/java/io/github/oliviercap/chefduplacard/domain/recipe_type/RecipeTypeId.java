package io.github.oliviercap.chefduplacard.domain.recipe_type;

import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;

public record RecipeTypeId(Long id) {
    public RecipeTypeId {
        if (id == null) {
            throw new DomainException(
                    "RecipeType id must not be null"
            );
        }

        if (id <= 0) {
            throw new DomainException(
                    "RecipeType id must be positive"
            );
        }
    }
}
