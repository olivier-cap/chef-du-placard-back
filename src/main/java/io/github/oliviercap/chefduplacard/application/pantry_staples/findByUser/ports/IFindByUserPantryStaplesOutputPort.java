package io.github.oliviercap.chefduplacard.application.pantry_staples.findByUser.ports;

import io.github.oliviercap.chefduplacard.adapters.web.pantry_staples.findByUser.FindByUserPantryStaplesViewModel;
import io.github.oliviercap.chefduplacard.application.pantry_staples.findByUser.FindByUserPantryStaplesResponseModel;

public interface IFindByUserPantryStaplesOutputPort {
    void displayPantryStaples(FindByUserPantryStaplesResponseModel responseModel);
    FindByUserPantryStaplesViewModel getViewModel();
}
