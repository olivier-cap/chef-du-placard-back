package io.github.oliviercap.chefduplacard.application.savenewmenu.port;

import io.github.oliviercap.chefduplacard.application.savenewmenu.SaveNewMenuRequestModel;

public interface ISaveNewMenuInputPort {
    void execute(SaveNewMenuRequestModel requestModel);
}
