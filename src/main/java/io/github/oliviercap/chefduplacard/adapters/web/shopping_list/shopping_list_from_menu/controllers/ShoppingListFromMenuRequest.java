package io.github.oliviercap.chefduplacard.adapters.web.shopping_list.shopping_list_from_menu.controllers;

public record ShoppingListFromMenuRequest(
        Long userId, Long menuId, Long stockId, Long pantryStaplesId
) {
}
