package io.github.oliviercap.chefduplacard.application.shopping_list.shopping_list_from_menu.ports;

import io.github.oliviercap.chefduplacard.adapters.web.shopping_list.shopping_list_from_menu.ShoppingListFromMenuViewModel;
import io.github.oliviercap.chefduplacard.application.htttpresponse.ShoppingListResponse;

public interface IShoppingListFromMenuOutputPort {
    void displayShoppingList(ShoppingListResponse shoppingListResponse);
    ShoppingListFromMenuViewModel getViewModel();
}
