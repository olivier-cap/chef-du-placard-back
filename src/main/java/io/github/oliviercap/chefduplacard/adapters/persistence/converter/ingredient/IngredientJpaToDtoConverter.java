package io.github.oliviercap.chefduplacard.adapters.persistence.converter.ingredient;

import io.github.oliviercap.chefduplacard.adapters.persistence.converter.unit.IUnitJpaToDtoConverter;
import io.github.oliviercap.chefduplacard.adapters.persistence.converter.aliment.IAlimentJpaToDtoConverter;
import io.github.oliviercap.chefduplacard.adapters.persistence.dto.IngredientDTO;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.IngredientJpa;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Helper to transform an Ingredient JPA Entity into DTO
 */
@Component
public class IngredientJpaToDtoConverter implements IIngredientJpaToDtoConverter {
    private final IAlimentJpaToDtoConverter alimentJpaToDtoConverter;
    private final IUnitJpaToDtoConverter unitJpaToDtoConverter;

    public IngredientJpaToDtoConverter(IAlimentJpaToDtoConverter alimentJpaToDtoConverter, IUnitJpaToDtoConverter unitJpaToDtoConverter) {
        this.alimentJpaToDtoConverter = alimentJpaToDtoConverter;
        this.unitJpaToDtoConverter = unitJpaToDtoConverter;
    }

    public IngredientDTO toDTO(IngredientJpa ingredientJpa) {
        Objects.requireNonNull(ingredientJpa, "ingredientJpa must not be null");
        Objects.requireNonNull(ingredientJpa.getAlimentJpa(), "alimentJpa must not be null");
        Objects.requireNonNull(ingredientJpa.getUnitJpa(), "untiJpa must not be null");

        return new IngredientDTO(
                ingredientJpa.getQuantityPerPerson(),
                alimentJpaToDtoConverter.toDTO(ingredientJpa.getAlimentJpa()),
                unitJpaToDtoConverter.toDTO(ingredientJpa.getUnitJpa())
        );
    }
}
