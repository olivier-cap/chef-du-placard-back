package io.github.oliviercap.chefduplacard.application.unit.get_all_units.ports;

import io.github.oliviercap.chefduplacard.adapters.web.unit.get_all_units.GetAllUnitsViewModel;
import io.github.oliviercap.chefduplacard.application.unit.get_all_units.GetAllUnitsResponseModel;

public interface IGetAllUnitsOutputPort {
    void displayUnits(GetAllUnitsResponseModel responseModel);
    GetAllUnitsViewModel getViewModel();
}
