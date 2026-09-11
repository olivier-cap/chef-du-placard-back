package io.github.oliviercap.chefduplacard.application.shopping_list.shopping_list_from_menu.ports;

import io.github.oliviercap.chefduplacard.application.shopping_list.shopping_list_from_menu.ShoppingListFromMenuRequestModel;

public interface IShoppingListFromMenuInputPort {
    void execute(ShoppingListFromMenuRequestModel requestModel);
}
