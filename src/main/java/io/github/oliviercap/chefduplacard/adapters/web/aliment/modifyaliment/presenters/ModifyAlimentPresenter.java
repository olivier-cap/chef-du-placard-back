package io.github.oliviercap.chefduplacard.adapters.web.aliment.modifyaliment.presenters;

import io.github.oliviercap.chefduplacard.adapters.web.aliment.modifyaliment.ModifyAlimentViewModel;
import io.github.oliviercap.chefduplacard.application.aliment.modifyaliment.ModifyAlimentResponseModel;
import io.github.oliviercap.chefduplacard.application.aliment.modifyaliment.ports.IModifyAlimentOutputPort;
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
