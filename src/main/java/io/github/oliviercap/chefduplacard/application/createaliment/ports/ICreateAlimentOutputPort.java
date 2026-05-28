package io.github.oliviercap.chefduplacard.application.createaliment.ports;

import io.github.oliviercap.chefduplacard.adapters.web.createaliment.CreateAlimentViewModel;
import io.github.oliviercap.chefduplacard.application.createaliment.CreateAlilmentResponseModel;

public interface ICreateAlimentOutputPort {
    void createAlimentResponse(CreateAlilmentResponseModel responseModel);
    CreateAlimentViewModel getViewModel();
}
