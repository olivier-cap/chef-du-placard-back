package io.github.oliviercap.chefduplacard.application.aliment.modifyaliment.ports;

import io.github.oliviercap.chefduplacard.adapters.web.aliment.modifyaliment.ModifyAlimentViewModel;
import io.github.oliviercap.chefduplacard.application.aliment.modifyaliment.ModifyAlimentResponseModel;

public interface IModifyAlimentOutputPort {
    void displayResponse(ModifyAlimentResponseModel responseModel);
    ModifyAlimentViewModel getViewModel();
}
