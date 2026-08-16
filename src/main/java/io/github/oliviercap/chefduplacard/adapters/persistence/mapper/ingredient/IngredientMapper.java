package io.github.oliviercap.chefduplacard.adapters.persistence.mapper.ingredient;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.IngredientJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.aliment.AlimentMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.unit.UnitMapper;
import io.github.oliviercap.chefduplacard.domain.food.Ingredient;
import io.github.oliviercap.chefduplacard.domain.food.IngredientId;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class IngredientMapper {
    private final AlimentMapper alimentMapper;
    private final UnitMapper unitMapper;

    public IngredientMapper(AlimentMapper alimentMapper,
                            UnitMapper unitMapper) {
        this.alimentMapper = alimentMapper;
        this.unitMapper = unitMapper;
    }

    public Ingredient toDomain(IngredientJpa ingredientJpa) {
        Objects.requireNonNull(ingredientJpa, "ingedientJpa must not be null");
        Objects.requireNonNull(ingredientJpa.getAlimentJpa(), "alimentJpa must not be null");
        Objects.requireNonNull(ingredientJpa.getUnitJpa(), "unitJpa must not be null");

        return new Ingredient(
                new IngredientId(ingredientJpa.getId()),
                ingredientJpa.getQuantityPerPerson(),
                alimentMapper.toDomain(ingredientJpa.getAlimentJpa()),
                unitMapper.toDomain(ingredientJpa.getUnitJpa())
        );
    }

}
