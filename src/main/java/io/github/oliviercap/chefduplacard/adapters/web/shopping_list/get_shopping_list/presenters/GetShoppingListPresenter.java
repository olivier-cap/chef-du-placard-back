package io.github.oliviercap.chefduplacard.adapters.web.shopping_list.get_shopping_list.presenters;

import io.github.oliviercap.chefduplacard.adapters.web.shopping_list.get_shopping_list.GetShoppingListViewModel;
import io.github.oliviercap.chefduplacard.application.shopping_list.get_shopping_list.GetShoppingListResponseModel;
import io.github.oliviercap.chefduplacard.application.shopping_list.get_shopping_list.ports.IGetShoppingListOutputPort;
import org.springframework.stereotype.Component;

@Component
public class GetShoppingListPresenter implements IGetShoppingListOutputPort {

    private GetShoppingListViewModel viewModel;

    @Override
    public void displayShoppingList(GetShoppingListResponseModel responseModel) {
        viewModel = new GetShoppingListViewModel(
                responseModel.shoppingListResponse().id(),
                responseModel.shoppingListResponse().shoppingListLineResponseList().stream()
                        .map(sl -> new GetShoppingListViewModel.ShoppingListLine(
                                sl.alimentResponse().name(),
                                sl.unitResponse().symbol(),
                                sl.quantity()
                                )
                        ).toList()
        );
    }

    @Override
    public GetShoppingListViewModel getViewModel() {
        return viewModel;
    }
}
