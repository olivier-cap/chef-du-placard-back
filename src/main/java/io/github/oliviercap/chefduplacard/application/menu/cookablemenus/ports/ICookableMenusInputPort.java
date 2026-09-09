package io.github.oliviercap.chefduplacard.application.menu.cookablemenus.ports;

import io.github.oliviercap.chefduplacard.application.menu.cookablemenus.CookableMenusRequestModel;

public interface ICookableMenusInputPort {
    void execute(CookableMenusRequestModel cookableMenusRequestModel);
}
