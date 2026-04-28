package io.github.oliviercap.chefduplacard.adapters.persistence.converter;

import io.github.oliviercap.chefduplacard.adapters.persistence.dto.IngredientDTO;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.IngredientJpa;

public interface IIngredientJpaToDtoConverter {
    IngredientDTO toDTO(IngredientJpa ingredientJpa);
}
