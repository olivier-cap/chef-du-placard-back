package io.github.oliviercap.chefduplacard.application.modifyaliment.ports;

import io.github.oliviercap.chefduplacard.adapters.web.modifyaliment.ModifyAlimentViewModel;
import io.github.oliviercap.chefduplacard.application.modifyaliment.ModifyAlimentResponseModel;

public interface IModifyAlimentOutputPort {
    void displayResponse(ModifyAlimentResponseModel responseModel);
    ModifyAlimentViewModel getViewModel();
}
