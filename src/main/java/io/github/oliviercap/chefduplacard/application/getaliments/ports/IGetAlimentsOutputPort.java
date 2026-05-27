package io.github.oliviercap.chefduplacard.application.getaliments.ports;

import io.github.oliviercap.chefduplacard.adapters.web.getaliments.GetAlimentsViewModel;
import io.github.oliviercap.chefduplacard.application.getaliments.GetAlimentsResponseModel;

public interface IGetAlimentsOutputPort {
    void displayAliments(GetAlimentsResponseModel responseModel);
    GetAlimentsViewModel getViewModel();
}
