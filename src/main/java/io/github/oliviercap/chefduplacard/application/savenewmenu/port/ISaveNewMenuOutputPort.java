package io.github.oliviercap.chefduplacard.application.savenewmenu.port;

import io.github.oliviercap.chefduplacard.adapters.web.savenewmenu.SaveNewMenuViewModel;
import io.github.oliviercap.chefduplacard.application.savenewmenu.SaveNewMenuResponseModel;

public interface ISaveNewMenuOutputPort {
    void saved(SaveNewMenuResponseModel responseModel);
    SaveNewMenuViewModel getViewModel();
}
