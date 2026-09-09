package io.github.oliviercap.chefduplacard.application.aliment.getaliments.ports;

import io.github.oliviercap.chefduplacard.application.aliment.getaliments.GetAlimentsRequestModel;

public interface IGetAlimentsInputPort {
    void execute(GetAlimentsRequestModel requestModel);
}
