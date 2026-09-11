package io.github.oliviercap.chefduplacard.adapters.web.shopping_list.get_shopping_list;

import java.math.BigDecimal;
import java.util.List;

public record GetShoppingListViewModel(
        Long id,
        List<ShoppingListLine> shoppingListLines
) {
    public record ShoppingListLine(
            String alimentName,
            String unitSymbol,
            BigDecimal quantity
    ) {}
}
