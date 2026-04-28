package io.github.oliviercap.chefduplacard.adapters.persistence.mapper.ingredient;

import io.github.oliviercap.chefduplacard.adapters.persistence.dto.IngredientDTO;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.aliment.IAlimentMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.unit.IUnitMapper;
import io.github.oliviercap.chefduplacard.domain.food.Ingredient;
import org.springframework.stereotype.Component;

@Component
public class IngredientMapper implements IIngredientMapper{
    private final IAlimentMapper alimentMapper;
    private final IUnitMapper unitMapper;

    public IngredientMapper(IAlimentMapper alimentMapper, IUnitMapper unitMapper) {
        this.alimentMapper = alimentMapper;
        this.unitMapper = unitMapper;
    }

    //suppose qu'on a créé un IngredientJpa avec toutes ses dépendances (=> son ingredient, sa quantite)
    //suppose qu'on a créé in AlimentDTO et un UnitDTO integres, dans le repository
    @Override
    public Ingredient toDomain(IngredientDTO ingredientDTO) {

        return new Ingredient(ingredientDTO.quantityPerPerson(),
                alimentMapper.toDomain(ingredientDTO.alimentDTO()),
                unitMapper.toDomain(ingredientDTO.unitDTO())
        );
    }
}
