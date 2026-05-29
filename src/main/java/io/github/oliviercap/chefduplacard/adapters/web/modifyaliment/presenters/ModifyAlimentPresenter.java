package io.github.oliviercap.chefduplacard.adapters.web.modifyaliment.presenters;

import io.github.oliviercap.chefduplacard.adapters.web.modifyaliment.ModifyAlimentViewModel;
import io.github.oliviercap.chefduplacard.application.modifyaliment.ModifyAlimentResponseModel;
import io.github.oliviercap.chefduplacard.application.modifyaliment.ports.IModifyAlimentOutputPort;
import org.springframework.stereotype.Component;

@Component
public class ModifyAlimentPresenter implements IModifyAlimentOutputPort {
    private ModifyAlimentViewModel viewModel;

    @Override
    public void displayResponse(ModifyAlimentResponseModel responseModel) {
        viewModel = new ModifyAlimentViewModel(responseModel.message());
    }

    @Override
    public ModifyAlimentViewModel getViewModel() {
        return viewModel;
    }
}
