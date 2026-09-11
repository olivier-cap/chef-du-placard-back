package io.github.oliviercap.chefduplacard.adapters.web.shopping_list.shopping_list_from_menu;

import java.math.BigDecimal;
import java.util.List;

public record ShoppingListFromMenuViewModel(
        Long id,
        List<ShoppingListLine> shoppingListLines
) {
    public record ShoppingListLine(
            String alimentName,
            String unitSymbol,
            BigDecimal quantity
    ) {}
}
