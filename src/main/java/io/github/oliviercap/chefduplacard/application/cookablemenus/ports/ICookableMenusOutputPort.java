package io.github.oliviercap.chefduplacard.application.cookablemenus.ports;

import io.github.oliviercap.chefduplacard.application.cookablemenus.CookableMenusResponseModel;

public interface ICookableMenusOutputPort {
    void displayCookableMenus(CookableMenusResponseModel cookableMenusResponseModel);
}
