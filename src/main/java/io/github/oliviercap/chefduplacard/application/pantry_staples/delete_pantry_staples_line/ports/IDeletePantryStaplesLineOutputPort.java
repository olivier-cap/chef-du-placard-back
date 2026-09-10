package io.github.oliviercap.chefduplacard.application.pantry_staples.delete_pantry_staples_line.ports;

import io.github.oliviercap.chefduplacard.adapters.web.pantry_staples.delete_pantry_staples_line.DeletePantryStaplesLineViewModel;
import io.github.oliviercap.chefduplacard.application.pantry_staples.delete_pantry_staples_line.DeletePantryStaplesLineResponseModel;

public interface IDeletePantryStaplesLineOutputPort {
    void displayResponse(DeletePantryStaplesLineResponseModel responseModel);
    DeletePantryStaplesLineViewModel getViewModel();
}
