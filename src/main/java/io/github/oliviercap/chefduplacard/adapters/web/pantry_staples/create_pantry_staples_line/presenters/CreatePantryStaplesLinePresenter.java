package io.github.oliviercap.chefduplacard.adapters.web.pantry_staples.create_pantry_staples_line.presenters;

import io.github.oliviercap.chefduplacard.adapters.web.pantry_staples.create_pantry_staples_line.CreatePantryStaplesLineViewModel;
import io.github.oliviercap.chefduplacard.application.pantry_staples.create_pantry_staples_line.CreatePantryStaplesLineResponseModel;
import io.github.oliviercap.chefduplacard.application.pantry_staples.create_pantry_staples_line.ports.ICreatePantryStaplesLineOutputPort;
import org.springframework.stereotype.Component;

@Component
public class CreatePantryStaplesLinePresenter implements ICreatePantryStaplesLineOutputPort {

    private CreatePantryStaplesLineViewModel viewModel;

    @Override
    public void displayNewPantryStaplesLine(CreatePantryStaplesLineResponseModel responseModel) {
        viewModel = new CreatePantryStaplesLineViewModel(
                responseModel.response().id(),
                responseModel.response().alimentResponse().name(),
                responseModel.response().unitResponse().symbol(),
                responseModel.response().quantity()
        );
    }

    @Override
    public CreatePantryStaplesLineViewModel getViewModel() {
        return viewModel;
    }
}
