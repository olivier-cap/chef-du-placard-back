package io.github.oliviercap.chefduplacard.application.aliment.modifyaliment.ports;

import io.github.oliviercap.chefduplacard.application.aliment.modifyaliment.ModifyAlimentRequestModel;

public interface IModifyAlimentInputPort {
    void execute(ModifyAlimentRequestModel requestModel);
}
