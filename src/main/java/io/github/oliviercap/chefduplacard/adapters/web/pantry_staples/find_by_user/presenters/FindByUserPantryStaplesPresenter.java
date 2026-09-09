package io.github.oliviercap.chefduplacard.adapters.web.pantry_staples.find_by_user.presenters;

import io.github.oliviercap.chefduplacard.adapters.web.pantry_staples.find_by_user.FindByUserPantryStaplesViewModel;
import io.github.oliviercap.chefduplacard.application.pantry_staples.find_by_user.FindByUserPantryStaplesResponseModel;
import io.github.oliviercap.chefduplacard.application.pantry_staples.find_by_user.ports.IFindByUserPantryStaplesOutputPort;
import org.springframework.stereotype.Component;

@Component
public class FindByUserPantryStaplesPresenter implements IFindByUserPantryStaplesOutputPort {

    private FindByUserPantryStaplesViewModel viewModel;


    @Override
    public void displayPantryStaples(FindByUserPantryStaplesResponseModel responseModel) {
        viewModel = new FindByUserPantryStaplesViewModel(
                responseModel.pantryStaplesResponse().id(),
                responseModel.pantryStaplesResponse().is_default(),
                responseModel.pantryStaplesResponse().staplesLineResponseList().stream()
                        .map(l ->
                                new FindByUserPantryStaplesViewModel.PantryStapleLine(
                                        l.id(),
                                        l.alimentResponse().name(),
                                        l.unitResponse().symbol(),
                                        l.quantity()
                                )
                        )
                        .toList()
        );
    }

    @Override
    public FindByUserPantryStaplesViewModel getViewModel() {
        return viewModel;
    }

}
