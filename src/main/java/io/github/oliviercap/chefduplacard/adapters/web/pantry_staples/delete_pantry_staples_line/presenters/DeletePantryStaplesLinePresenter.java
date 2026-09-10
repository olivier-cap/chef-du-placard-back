package io.github.oliviercap.chefduplacard.adapters.web.pantry_staples.delete_pantry_staples_line.presenters;

import io.github.oliviercap.chefduplacard.adapters.web.pantry_staples.delete_pantry_staples_line.DeletePantryStaplesLineViewModel;
import io.github.oliviercap.chefduplacard.application.pantry_staples.delete_pantry_staples_line.DeletePantryStaplesLineResponseModel;
import io.github.oliviercap.chefduplacard.application.pantry_staples.delete_pantry_staples_line.ports.IDeletePantryStaplesLineOutputPort;
import org.springframework.stereotype.Component;

@Component
public class DeletePantryStaplesLinePresenter implements IDeletePantryStaplesLineOutputPort {

    private DeletePantryStaplesLineViewModel viewModel;

    @Override
    public void displayResponse(DeletePantryStaplesLineResponseModel responseModel) {
        viewModel = new DeletePantryStaplesLineViewModel(responseModel.isDeleted());
    }

    @Override
    public DeletePantryStaplesLineViewModel getViewModel() {
        return viewModel;
    }
}
