package io.github.oliviercap.chefduplacard.adapters.web.createaliment.presenters;

import io.github.oliviercap.chefduplacard.adapters.web.createaliment.CreateAlimentViewModel;
import io.github.oliviercap.chefduplacard.application.createaliment.CreateAlilmentResponseModel;
import io.github.oliviercap.chefduplacard.application.createaliment.ports.ICreateAlimentOutputPort;
import org.springframework.stereotype.Component;

@Component
public class CreateAlimentPresenter implements ICreateAlimentOutputPort {

    private CreateAlimentViewModel viewModel;

    @Override
    public void createAlimentResponse(CreateAlilmentResponseModel responseModel) {
        viewModel = new CreateAlimentViewModel(responseModel.response());
    }

    @Override
    public CreateAlimentViewModel getViewModel() {
        return viewModel;
    }
}
