package io.github.oliviercap.chefduplacard.application.getmenu.ports;

import io.github.oliviercap.chefduplacard.adapters.web.getmenu.GetMenuViewModel;
import io.github.oliviercap.chefduplacard.application.getmenu.GetMenuResponseModel;

public interface IGetMenuOutputPort {
    void displayMenu(GetMenuResponseModel responseModel);
    GetMenuViewModel getViewModel();
}
