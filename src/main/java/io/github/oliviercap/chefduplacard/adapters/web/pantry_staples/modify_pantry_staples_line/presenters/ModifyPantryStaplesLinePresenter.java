package io.github.oliviercap.chefduplacard.adapters.web.pantry_staples.modify_pantry_staples_line.presenters;

import io.github.oliviercap.chefduplacard.adapters.web.pantry_staples.modify_pantry_staples_line.ModifyPantryStaplesLineViewModel;
import io.github.oliviercap.chefduplacard.application.pantry_staples.modify_pantry_staples_line.ModifyPantryStaplesLineResponseModel;
import io.github.oliviercap.chefduplacard.application.pantry_staples.modify_pantry_staples_line.ports.IModifyPantryStaplesLineOutputPort;
import org.springframework.stereotype.Component;

@Component
public class ModifyPantryStaplesLinePresenter implements IModifyPantryStaplesLineOutputPort {

    private ModifyPantryStaplesLineViewModel viewModel;

    @Override
    public void displayNewPantryStapleLine(ModifyPantryStaplesLineResponseModel responseModel) {
        viewModel = new ModifyPantryStaplesLineViewModel(
                responseModel.response().id(),
                responseModel.response().alimentResponse().name(),
                responseModel.response().unitResponse().symbol(),
                responseModel.response().quantity()
        );
    }

    @Override
    public ModifyPantryStaplesLineViewModel getViewModel() {
        return viewModel;
    }
}
