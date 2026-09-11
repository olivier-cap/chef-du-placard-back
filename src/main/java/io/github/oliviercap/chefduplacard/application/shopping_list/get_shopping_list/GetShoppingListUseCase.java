package io.github.oliviercap.chefduplacard.application.shopping_list.get_shopping_list;

import io.github.oliviercap.chefduplacard.application.htttpresponse.ShoppingListResponse;
import io.github.oliviercap.chefduplacard.application.shopping_list.get_shopping_list.ports.IGetShoppingListInputPort;
import io.github.oliviercap.chefduplacard.application.shopping_list.get_shopping_list.ports.IGetShoppingListOutputPort;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IShoppingListRepository;
import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import io.github.oliviercap.chefduplacard.domain.shopping_list.ShoppingList;

public class GetShoppingListUseCase implements IGetShoppingListInputPort {

    private final IShoppingListRepository shoppingListRepository;
    private final IGetShoppingListOutputPort outputPort;

    public GetShoppingListUseCase(IShoppingListRepository shoppingListRepository, IGetShoppingListOutputPort outputPort) {
        this.shoppingListRepository = shoppingListRepository;
        this.outputPort = outputPort;
    }


    @Override
    public void execute(GetShoppingListRequestModel requestModel) {
        ShoppingList shoppingList = getShoppingList(requestModel.shoppingListId());

        outputPort.displayShoppingList(new GetShoppingListResponseModel(
                ShoppingListResponse.from(shoppingList)
        ));
    }

    private ShoppingList getShoppingList(Long shoppingListId) {
            return shoppingListRepository.findById(shoppingListId)
                    .orElseThrow(() -> new DomainException("shopping list not found"));
    }
}
