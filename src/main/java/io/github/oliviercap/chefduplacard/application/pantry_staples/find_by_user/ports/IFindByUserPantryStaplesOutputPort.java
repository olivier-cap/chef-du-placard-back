package io.github.oliviercap.chefduplacard.application.pantry_staples.find_by_user.ports;

import io.github.oliviercap.chefduplacard.adapters.web.pantry_staples.find_by_user.FindByUserPantryStaplesViewModel;
import io.github.oliviercap.chefduplacard.application.pantry_staples.find_by_user.FindByUserPantryStaplesResponseModel;

public interface IFindByUserPantryStaplesOutputPort {
    void displayPantryStaples(FindByUserPantryStaplesResponseModel responseModel);
    FindByUserPantryStaplesViewModel getViewModel();
}
