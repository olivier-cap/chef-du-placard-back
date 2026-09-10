package io.github.oliviercap.chefduplacard.application.pantry_staples.create_pantry_staples_line.ports;

import io.github.oliviercap.chefduplacard.application.pantry_staples.create_pantry_staples_line.CreatePantryStaplesLineRequestModel;

public interface ICreatePantryStaplesLineInputPort {
    void execute(CreatePantryStaplesLineRequestModel requestModel);
}
