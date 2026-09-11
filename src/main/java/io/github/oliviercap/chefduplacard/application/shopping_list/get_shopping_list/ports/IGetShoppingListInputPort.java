package io.github.oliviercap.chefduplacard.application.shopping_list.get_shopping_list.ports;

import io.github.oliviercap.chefduplacard.application.shopping_list.get_shopping_list.GetShoppingListRequestModel;

public interface IGetShoppingListInputPort {
    void execute(GetShoppingListRequestModel requestModel);
}
