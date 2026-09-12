package io.github.oliviercap.chefduplacard.application.menu.get_all_menus.ports;

import io.github.oliviercap.chefduplacard.adapters.web.menu.get_all_menus.GetAllMenusViewModel;
import io.github.oliviercap.chefduplacard.application.menu.get_all_menus.GetAllMenusResponseModel;

public interface IGetAllMenusOutputPort {
    void displayMenus(GetAllMenusResponseModel responseModel);
    GetAllMenusViewModel getViewModel();
}
