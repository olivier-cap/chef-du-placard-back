package io.github.oliviercap.chefduplacard.adapters.web.shopping_list.shopping_list_from_menu.presenters;

import io.github.oliviercap.chefduplacard.adapters.web.shopping_list.shopping_list_from_menu.ShoppingListFromMenuViewModel;
import io.github.oliviercap.chefduplacard.application.htttpresponse.ShoppingListResponse;
import io.github.oliviercap.chefduplacard.application.shopping_list.shopping_list_from_menu.ports.IShoppingListFromMenuOutputPort;
import org.springframework.stereotype.Component;

@Component
public class ShoppingListFromMenuPresenter implements IShoppingListFromMenuOutputPort {

    private ShoppingListFromMenuViewModel viewModel;

    @Override
    public void displayShoppingList(ShoppingListResponse shoppingListResponse) {
        viewModel = new ShoppingListFromMenuViewModel(
                shoppingListResponse.id(),
                shoppingListResponse.shoppingListLineResponseList().stream()
                        .map(
                                line -> new ShoppingListFromMenuViewModel.ShoppingListLine(
                                        line.alimentResponse().name(),
                                        line.unitResponse().symbol(),
                                        line.quantity()
                                )
                        )
                        .toList()
        );
    }

    @Override
    public ShoppingListFromMenuViewModel getViewModel() {
        return viewModel;
    }
}
