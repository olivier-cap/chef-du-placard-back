package io.github.oliviercap.chefduplacard.adapters.web.shopping_list.get_shopping_list.controllers;

import io.github.oliviercap.chefduplacard.adapters.web.shopping_list.get_shopping_list.GetShoppingListViewModel;
import io.github.oliviercap.chefduplacard.application.shopping_list.get_shopping_list.GetShoppingListRequestModel;
import io.github.oliviercap.chefduplacard.application.shopping_list.get_shopping_list.ports.IGetShoppingListInputPort;
import io.github.oliviercap.chefduplacard.application.shopping_list.get_shopping_list.ports.IGetShoppingListOutputPort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetShoppingListController {

    private final IGetShoppingListInputPort inputPort;
    private final IGetShoppingListOutputPort outputPort;


    public GetShoppingListController(
            IGetShoppingListInputPort inputPort,
            IGetShoppingListOutputPort outputPort
    ) {
        this.inputPort = inputPort;
        this.outputPort = outputPort;
    }

    @GetMapping("/api/getshoppinglist")
    public GetShoppingListViewModel getShoppingList(
            @PathVariable Long shoppingListId
    ) {
        inputPort.execute(new GetShoppingListRequestModel(shoppingListId));

        return outputPort.getViewModel();
    }
}
