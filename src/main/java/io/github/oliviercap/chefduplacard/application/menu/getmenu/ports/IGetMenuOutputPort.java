package io.github.oliviercap.chefduplacard.application.menu.getmenu.ports;

import io.github.oliviercap.chefduplacard.adapters.web.menu.getmenu.GetMenuViewModel;
import io.github.oliviercap.chefduplacard.application.menu.getmenu.GetMenuResponseModel;

public interface IGetMenuOutputPort {
    void displayMenu(GetMenuResponseModel responseModel);
    GetMenuViewModel getViewModel();
}
