package io.github.oliviercap.chefduplacard.adapters.web.shopping_list.shopping_list_from_menu.controllers;

import io.github.oliviercap.chefduplacard.adapters.web.shopping_list.shopping_list_from_menu.ShoppingListFromMenuViewModel;
import io.github.oliviercap.chefduplacard.application.shopping_list.shopping_list_from_menu.ShoppingListFromMenuRequestModel;
import io.github.oliviercap.chefduplacard.application.shopping_list.shopping_list_from_menu.ports.IShoppingListFromMenuInputPort;
import io.github.oliviercap.chefduplacard.application.shopping_list.shopping_list_from_menu.ports.IShoppingListFromMenuOutputPort;
import org.springframework.web.bind.annotation.*;

@RestController
public class ShoppingListFromMenuController {

    private final IShoppingListFromMenuInputPort inputPort;
    private final IShoppingListFromMenuOutputPort outputPort;


    public ShoppingListFromMenuController(
            IShoppingListFromMenuInputPort inputPort,
            IShoppingListFromMenuOutputPort outputPort
    ) {
        this.inputPort = inputPort;
        this.outputPort = outputPort;
    }

    @GetMapping("/api/shoppinglistmenu")
    public ShoppingListFromMenuViewModel shoppingListFromMenu(
        @RequestParam Long userId,
        @RequestParam Long menuId,
        @RequestParam Long stockId,
        @RequestParam Long pantryStaplesId
    ) {

        inputPort.execute(new ShoppingListFromMenuRequestModel(
                userId, menuId, stockId, pantryStaplesId
        ));

        return outputPort.getViewModel();
    }
}
