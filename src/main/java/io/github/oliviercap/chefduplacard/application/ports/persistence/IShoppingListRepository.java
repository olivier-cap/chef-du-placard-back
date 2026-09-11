package io.github.oliviercap.chefduplacard.application.ports.persistence;

import io.github.oliviercap.chefduplacard.domain.shopping_list.ShoppingList;
import io.github.oliviercap.chefduplacard.domain.shopping_list.ShoppingListLine;

import java.util.List;
import java.util.Optional;

public interface IShoppingListRepository {

    ShoppingList createNewFromShoppingListLines(Long userId, List<ShoppingListLine> shoppingListLines);
    Optional<ShoppingList> findById(Long shoppingListId);
}
