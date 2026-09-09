package io.github.oliviercap.chefduplacard.application.menu.savenewmenu.port;

import io.github.oliviercap.chefduplacard.application.menu.savenewmenu.SaveNewMenuRequestModel;

public interface ISaveNewMenuInputPort {
    void execute(SaveNewMenuRequestModel requestModel);
}
