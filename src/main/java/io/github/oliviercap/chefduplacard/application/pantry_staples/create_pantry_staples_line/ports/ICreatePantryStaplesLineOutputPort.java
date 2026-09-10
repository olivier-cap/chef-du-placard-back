package io.github.oliviercap.chefduplacard.application.pantry_staples.create_pantry_staples_line.ports;

import io.github.oliviercap.chefduplacard.adapters.web.pantry_staples.create_pantry_staples_line.CreatePantryStaplesLineViewModel;
import io.github.oliviercap.chefduplacard.application.pantry_staples.create_pantry_staples_line.CreatePantryStaplesLineResponseModel;

public interface ICreatePantryStaplesLineOutputPort {
    void displayNewPantryStaplesLine(CreatePantryStaplesLineResponseModel responseModel);
    CreatePantryStaplesLineViewModel getViewModel();
}
