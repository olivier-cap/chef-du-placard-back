package io.github.oliviercap.chefduplacard.application.cookablemenus.ports;

import io.github.oliviercap.chefduplacard.application.cookablemenus.CookableMenusRequestModel;

public interface ICookableMenusInputPort {
    void execute(CookableMenusRequestModel cookableMenusRequestModel);
}
