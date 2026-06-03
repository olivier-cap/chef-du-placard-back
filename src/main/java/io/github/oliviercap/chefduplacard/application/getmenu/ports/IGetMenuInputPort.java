package io.github.oliviercap.chefduplacard.application.getmenu.ports;

import io.github.oliviercap.chefduplacard.application.getmenu.GetMenuRequestModel;

public interface IGetMenuInputPort {
    void execute(GetMenuRequestModel requestModel);
}
