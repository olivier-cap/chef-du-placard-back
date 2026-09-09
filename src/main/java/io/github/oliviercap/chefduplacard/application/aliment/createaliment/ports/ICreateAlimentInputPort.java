package io.github.oliviercap.chefduplacard.application.aliment.createaliment.ports;

import io.github.oliviercap.chefduplacard.application.aliment.createaliment.CreateAlimentRequestModel;

public interface ICreateAlimentInputPort {
    void execute (CreateAlimentRequestModel requestModel);
}
