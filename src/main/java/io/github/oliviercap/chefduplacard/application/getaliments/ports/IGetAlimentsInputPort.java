package io.github.oliviercap.chefduplacard.application.getaliments.ports;

import io.github.oliviercap.chefduplacard.application.getaliments.GetAlimentsRequestModel;

public interface IGetAlimentsInputPort {
    void execute(GetAlimentsRequestModel requestModel);
}
