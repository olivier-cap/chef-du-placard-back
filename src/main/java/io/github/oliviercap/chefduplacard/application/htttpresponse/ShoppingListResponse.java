package io.github.oliviercap.chefduplacard.application.htttpresponse;

import io.github.oliviercap.chefduplacard.domain.shopping_list.ShoppingList;

import java.time.LocalDate;
import java.util.List;

public record ShoppingListResponse(
        Long id,
        UserResponse userResponse,
        LocalDate date,
        List<ShoppingListLineResponse> shoppingListLineResponseList
) {
    public static ShoppingListResponse from(ShoppingList shoppingList){
        return new ShoppingListResponse(
                shoppingList.getId().id(),
                UserResponse.from(shoppingList.getUser()),
                shoppingList.getDate(),
                shoppingList.getLineList().stream()
                        .map(ShoppingListLineResponse::from)
                        .toList()
        );
    }
}
