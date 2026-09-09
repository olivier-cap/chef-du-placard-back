package io.github.oliviercap.chefduplacard.application.aliment.createaliment.ports;

import io.github.oliviercap.chefduplacard.adapters.web.aliment.createaliment.CreateAlimentViewModel;
import io.github.oliviercap.chefduplacard.application.aliment.createaliment.CreateAlilmentResponseModel;

public interface ICreateAlimentOutputPort {
    void createAlimentResponse(CreateAlilmentResponseModel responseModel);
    CreateAlimentViewModel getViewModel();
}
