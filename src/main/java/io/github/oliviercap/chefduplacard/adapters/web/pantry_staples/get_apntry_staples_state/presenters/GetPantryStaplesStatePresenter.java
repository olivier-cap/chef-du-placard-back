package io.github.oliviercap.chefduplacard.adapters.web.pantry_staples.get_apntry_staples_state.presenters;

import io.github.oliviercap.chefduplacard.adapters.web.pantry_staples.get_apntry_staples_state.GetPantryStaplesStateViewModel;
import io.github.oliviercap.chefduplacard.application.pantry_staples.get_pantry_staples_state.GetPantryStaplesStateResponseModel;
import io.github.oliviercap.chefduplacard.application.pantry_staples.get_pantry_staples_state.ports.IGetPantryStaplesStateOutputPort;
import org.springframework.stereotype.Component;

@Component
public class GetPantryStaplesStatePresenter implements IGetPantryStaplesStateOutputPort {
    @Override
    public void displayPantryStaplesState(GetPantryStaplesStateResponseModel responseModel) {

    }

    @Override
    public GetPantryStaplesStateViewModel getViewModel() {
        return null;
    }
}
