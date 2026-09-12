package io.github.oliviercap.chefduplacard.application.menu.get_all_menus.ports;

import io.github.oliviercap.chefduplacard.application.menu.get_all_menus.GetAllMenusRequestModel;

public interface IGetAllMenusInputPort {
    void execute(GetAllMenusRequestModel requestModel);
}
