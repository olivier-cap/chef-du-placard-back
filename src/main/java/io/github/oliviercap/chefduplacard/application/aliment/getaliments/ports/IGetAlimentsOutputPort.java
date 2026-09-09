package io.github.oliviercap.chefduplacard.application.aliment.getaliments.ports;

import io.github.oliviercap.chefduplacard.adapters.web.aliment.getaliments.GetAlimentsViewModel;
import io.github.oliviercap.chefduplacard.application.aliment.getaliments.GetAlimentsResponseModel;

public interface IGetAlimentsOutputPort {
    void displayAliments(GetAlimentsResponseModel responseModel);
    GetAlimentsViewModel getViewModel();
}
