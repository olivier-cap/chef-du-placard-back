package io.github.oliviercap.chefduplacard.application.pantry_staples.delete_pantry_staples_line.ports;

import io.github.oliviercap.chefduplacard.application.pantry_staples.delete_pantry_staples_line.DeletePantryStaplesLineRequestModel;

public interface IDeletePantryStaplesLineInputPort {
    void execute(DeletePantryStaplesLineRequestModel requestModel);
}
