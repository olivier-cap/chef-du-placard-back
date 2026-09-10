package io.github.oliviercap.chefduplacard.application.pantry_staples.modify_pantry_staples_line.ports;

import io.github.oliviercap.chefduplacard.adapters.web.pantry_staples.modify_pantry_staples_line.ModifyPantryStaplesLineViewModel;
import io.github.oliviercap.chefduplacard.application.pantry_staples.modify_pantry_staples_line.ModifyPantryStaplesLineResponseModel;

public interface IModifyPantryStaplesLineOutputPort {
    void displayNewPantryStapleLine(ModifyPantryStaplesLineResponseModel responseModel);
    ModifyPantryStaplesLineViewModel getViewModel();
}
