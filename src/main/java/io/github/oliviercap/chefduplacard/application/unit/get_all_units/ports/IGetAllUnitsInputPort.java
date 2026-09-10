package io.github.oliviercap.chefduplacard.application.unit.get_all_units.ports;

import io.github.oliviercap.chefduplacard.application.unit.get_all_units.GetAllUnitsRequestModel;

public interface IGetAllUnitsInputPort {
    void execute(GetAllUnitsRequestModel requestModel);
}
