package io.github.oliviercap.chefduplacard.application.shopping_list.get_shopping_list.ports;

import io.github.oliviercap.chefduplacard.adapters.web.shopping_list.get_shopping_list.GetShoppingListViewModel;
import io.github.oliviercap.chefduplacard.application.shopping_list.get_shopping_list.GetShoppingListResponseModel;

public interface IGetShoppingListOutputPort {
    void displayShoppingList(GetShoppingListResponseModel responseModel);
    GetShoppingListViewModel getViewModel();
}
