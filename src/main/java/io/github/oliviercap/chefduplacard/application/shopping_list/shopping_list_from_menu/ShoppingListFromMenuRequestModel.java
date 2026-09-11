package io.github.oliviercap.chefduplacard.application.shopping_list.shopping_list_from_menu;

public record ShoppingListFromMenuRequestModel(Long userId, Long menuId, Long stockId, Long pantryStaplesId) {
}
