package io.github.oliviercap.chefduplacard.application.pantry_staples.modify_pantry_staples_line.ports;

import io.github.oliviercap.chefduplacard.application.pantry_staples.modify_pantry_staples_line.ModifyPantryStaplesLineRequestModel;

public interface IModifyPantryStaplesLineInputPort {
    void execute(ModifyPantryStaplesLineRequestModel requestModel);
}
