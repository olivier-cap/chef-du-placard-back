package io.github.oliviercap.chefduplacard.application.pantry_staples.get_pantry_staples_state.ports;

import io.github.oliviercap.chefduplacard.adapters.web.pantry_staples.get_apntry_staples_state.GetPantryStaplesStateViewModel;
import io.github.oliviercap.chefduplacard.application.pantry_staples.get_pantry_staples_state.GetPantryStaplesStateResponseModel;

public interface IGetPantryStaplesStateOutputPort {
    void displayPantryStaplesState(GetPantryStaplesStateResponseModel responseModel);
    GetPantryStaplesStateViewModel getViewModel();
}
