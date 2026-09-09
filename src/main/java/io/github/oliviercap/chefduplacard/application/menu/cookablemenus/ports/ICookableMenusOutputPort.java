package io.github.oliviercap.chefduplacard.application.menu.cookablemenus.ports;

import io.github.oliviercap.chefduplacard.application.menu.cookablemenus.CookableMenusResponseModel;

public interface ICookableMenusOutputPort {
    void displayCookableMenus(CookableMenusResponseModel cookableMenusResponseModel);
}
