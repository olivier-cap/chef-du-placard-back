package io.github.oliviercap.chefduplacard.application.modifyaliment.ports;

import io.github.oliviercap.chefduplacard.application.modifyaliment.ModifyAlimentRequestModel;

public interface IModifyAlimentInputPort {
    void execute(ModifyAlimentRequestModel requestModel);
}
