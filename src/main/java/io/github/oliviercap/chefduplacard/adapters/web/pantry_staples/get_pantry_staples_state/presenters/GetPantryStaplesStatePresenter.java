package io.github.oliviercap.chefduplacard.adapters.web.pantry_staples.get_pantry_staples_state.presenters;

import io.github.oliviercap.chefduplacard.adapters.web.pantry_staples.get_pantry_staples_state.GetPantryStaplesStateViewModel;
import io.github.oliviercap.chefduplacard.application.pantry_staples.get_pantry_staples_state.GetPantryStaplesStateResponseModel;
import io.github.oliviercap.chefduplacard.application.pantry_staples.get_pantry_staples_state.ports.IGetPantryStaplesStateOutputPort;
import org.springframework.stereotype.Component;

@Component
public class GetPantryStaplesStatePresenter implements IGetPantryStaplesStateOutputPort {

    private GetPantryStaplesStateViewModel viewModel;

    @Override
    public void displayPantryStaplesState(GetPantryStaplesStateResponseModel responseModel) {
        viewModel = new GetPantryStaplesStateViewModel(
                responseModel.stateLineList().stream()
                        .map(
                                stateLine -> new GetPantryStaplesStateViewModel.PantryStapleStateLine(
                                        stateLine.pantryStaplesLineResponse().id(),
                                        stateLine.pantryStaplesLineResponse().alimentResponse().name(),
                                        stateLine.pantryStaplesLineResponse().unitResponse().symbol(),
                                        stateLine.pantryStaplesLineResponse().quantity(),
                                        stateLine.actualQuantity(),
                                        stateLine.isSufficient()
                                )
                        ).toList()
        );
    }

    @Override
    public GetPantryStaplesStateViewModel getViewModel() {
        return viewModel;
    }
}
