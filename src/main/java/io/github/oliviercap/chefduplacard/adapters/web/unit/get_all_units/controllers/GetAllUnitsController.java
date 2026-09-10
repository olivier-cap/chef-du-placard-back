package io.github.oliviercap.chefduplacard.adapters.web.unit.get_all_units.controllers;

import io.github.oliviercap.chefduplacard.adapters.web.unit.get_all_units.GetAllUnitsViewModel;
import io.github.oliviercap.chefduplacard.application.unit.get_all_units.GetAllUnitsRequestModel;
import io.github.oliviercap.chefduplacard.application.unit.get_all_units.ports.IGetAllUnitsInputPort;
import io.github.oliviercap.chefduplacard.application.unit.get_all_units.ports.IGetAllUnitsOutputPort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetAllUnitsController {

    private final IGetAllUnitsInputPort inputPort;
    private final IGetAllUnitsOutputPort outputPort;

    public GetAllUnitsController(
            IGetAllUnitsInputPort inputPort,
            IGetAllUnitsOutputPort outputPort
    ) {
        this.inputPort = inputPort;
        this.outputPort = outputPort;
    }

    @GetMapping("/api/getallunits")
    public GetAllUnitsViewModel getAllUnits() {
        inputPort.execute(new GetAllUnitsRequestModel());

        return outputPort.getViewModel();
    }
}
