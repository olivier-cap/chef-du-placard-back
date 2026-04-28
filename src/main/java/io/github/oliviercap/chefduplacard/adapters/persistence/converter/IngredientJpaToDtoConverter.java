package io.github.oliviercap.chefduplacard.adapters.persistence.converter;

import io.github.oliviercap.chefduplacard.adapters.persistence.dto.IngredientDTO;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.IngredientJpa;
import org.springframework.stereotype.Component;

@Component
public class IngredientJpaToDtoConverter implements IIngredientJpaToDtoConverter {
    private final IAlimentJpaToDtoConverter alimentJpaToDtoConverter;
    private final IUnitJpaToDtoConverter unitJpaToDtoConverter;

    public IngredientJpaToDtoConverter(IAlimentJpaToDtoConverter alimentJpaToDtoConverter, IUnitJpaToDtoConverter unitJpaToDtoConverter) {
        this.alimentJpaToDtoConverter = alimentJpaToDtoConverter;
        this.unitJpaToDtoConverter = unitJpaToDtoConverter;
    }

    public IngredientDTO toDTO(IngredientJpa ingredientJpa) {
        return new IngredientDTO(
                ingredientJpa.getQuantityPerPerson(),
                alimentJpaToDtoConverter.toDTO(ingredientJpa.getAlimentJpa()),
                unitJpaToDtoConverter.toDTO(ingredientJpa.getUnitJpa())
        );
    }
}
