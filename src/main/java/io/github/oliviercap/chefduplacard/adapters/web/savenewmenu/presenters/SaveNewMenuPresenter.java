package io.github.oliviercap.chefduplacard.adapters.web.savenewmenu.presenters;

import io.github.oliviercap.chefduplacard.adapters.web.savenewmenu.SaveNewMenuViewModel;
import io.github.oliviercap.chefduplacard.application.savenewmenu.SaveNewMenuResponseModel;
import io.github.oliviercap.chefduplacard.application.savenewmenu.port.ISaveNewMenuOutputPort;
import org.springframework.stereotype.Component;

@Component
public class SaveNewMenuPresenter implements ISaveNewMenuOutputPort {

    private SaveNewMenuViewModel viewModel;

    @Override
    public void saved(SaveNewMenuResponseModel responseModel) {
        viewModel = new SaveNewMenuViewModel(responseModel.saved());
    }

    @Override
    public SaveNewMenuViewModel getViewModel() {
        return viewModel;
    }
}
