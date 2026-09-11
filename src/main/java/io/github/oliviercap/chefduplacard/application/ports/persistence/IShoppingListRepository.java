package io.github.oliviercap.chefduplacard.application.ports.persistence;

import io.github.oliviercap.chefduplacard.domain.shopping_list.ShoppingList;
import io.github.oliviercap.chefduplacard.domain.shopping_list.ShoppingListLine;

import java.util.List;

public interface IShoppingListRepository {

    ShoppingList createNewFromShoppingListLines(Long userId, List<ShoppingListLine> shoppingListLines);
}
