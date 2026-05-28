package io.github.oliviercap.chefduplacard.application.createaliment.ports;

import io.github.oliviercap.chefduplacard.application.createaliment.CreateAlimentRequestModel;

public interface ICreateAlimentInputPort {
    void execute (CreateAlimentRequestModel requestModel);
}
