package io.github.oliviercap.chefduplacard.application.menu.savenewmenu.port;

import io.github.oliviercap.chefduplacard.adapters.web.menu.savenewmenu.SaveNewMenuViewModel;
import io.github.oliviercap.chefduplacard.application.menu.savenewmenu.SaveNewMenuResponseModel;

public interface ISaveNewMenuOutputPort {
    void saved(SaveNewMenuResponseModel responseModel);
    SaveNewMenuViewModel getViewModel();
}
