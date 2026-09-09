package io.github.oliviercap.chefduplacard.application.menu.getmenu.ports;

import io.github.oliviercap.chefduplacard.application.menu.getmenu.GetMenuRequestModel;

public interface IGetMenuInputPort {
    void execute(GetMenuRequestModel requestModel);
}
