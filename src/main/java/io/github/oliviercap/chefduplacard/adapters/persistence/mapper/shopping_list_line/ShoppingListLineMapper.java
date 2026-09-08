package io.github.oliviercap.chefduplacard.adapters.persistence.mapper.shopping_list_line;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.ShoppingListLineJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.aliment.AlimentMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.unit.UnitMapper;
import io.github.oliviercap.chefduplacard.domain.shopping_list.ShoppingListLine;
import io.github.oliviercap.chefduplacard.domain.shopping_list.ShoppingListLineId;
import org.springframework.stereotype.Component;

@Component
public class ShoppingListLineMapper {

    private final AlimentMapper alimentMapper;
    private final UnitMapper unitMapper;

    public ShoppingListLineMapper(AlimentMapper alimentMapper, UnitMapper unitMapper) {
        this.alimentMapper = alimentMapper;
        this.unitMapper = unitMapper;
    }


    public ShoppingListLine toDomain (ShoppingListLineJpa shoppingListLineJpa) {
        return new ShoppingListLine(
                new ShoppingListLineId(shoppingListLineJpa.getId()),
                alimentMapper.toDomain(shoppingListLineJpa.getAlimentJpa()),
                unitMapper.toDomain(shoppingListLineJpa.getUnitJpa()),
                shoppingListLineJpa.getQuantity()
        );
    }
}
