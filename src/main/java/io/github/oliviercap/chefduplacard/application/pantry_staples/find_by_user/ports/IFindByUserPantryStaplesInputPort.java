package io.github.oliviercap.chefduplacard.application.pantry_staples.find_by_user.ports;

import io.github.oliviercap.chefduplacard.application.pantry_staples.find_by_user.FindByUserPantryStaplesRequestModel;

public interface IFindByUserPantryStaplesInputPort {
    void execute(FindByUserPantryStaplesRequestModel requestModel);

}
