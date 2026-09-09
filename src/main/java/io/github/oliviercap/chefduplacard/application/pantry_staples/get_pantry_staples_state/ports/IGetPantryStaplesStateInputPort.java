package io.github.oliviercap.chefduplacard.application.pantry_staples.get_pantry_staples_state.ports;

import io.github.oliviercap.chefduplacard.application.pantry_staples.get_pantry_staples_state.GetPantryStaplesStateRequestModel;

public interface IGetPantryStaplesStateInputPort {
    void execute(GetPantryStaplesStateRequestModel requestModel);
}
