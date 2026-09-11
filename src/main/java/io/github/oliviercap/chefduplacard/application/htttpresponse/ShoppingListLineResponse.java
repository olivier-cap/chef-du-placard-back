package io.github.oliviercap.chefduplacard.application.htttpresponse;

import io.github.oliviercap.chefduplacard.domain.shopping_list.ShoppingListLine;

import java.math.BigDecimal;

public record ShoppingListLineResponse(
        Long id,
        AlimentResponse alimentResponse,
        UnitResponse unitResponse,
        BigDecimal quantity
) {

    public static ShoppingListLineResponse from(ShoppingListLine shoppingListLine) {
        return new ShoppingListLineResponse(
                shoppingListLine.getId().id(),
                AlimentResponse.from(shoppingListLine.getAliment()),
                UnitResponse.from(shoppingListLine.getUnit()),
                shoppingListLine.getQuantity()
        );
    }
}
