package io.github.oliviercap.chefduplacard.application.pantry_staples.findByUser.ports;

import io.github.oliviercap.chefduplacard.application.pantry_staples.findByUser.FindByUserPantryStaplesRequestModel;

public interface IFindByUserPantryStaplesInputPort {
    void execute(FindByUserPantryStaplesRequestModel requestModel);

}
