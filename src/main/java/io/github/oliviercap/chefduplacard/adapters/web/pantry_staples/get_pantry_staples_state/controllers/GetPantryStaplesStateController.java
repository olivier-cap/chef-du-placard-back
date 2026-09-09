package io.github.oliviercap.chefduplacard.adapters.web.pantry_staples.get_pantry_staples_state.controllers;

import io.github.oliviercap.chefduplacard.adapters.web.pantry_staples.get_pantry_staples_state.GetPantryStaplesStateViewModel;
import io.github.oliviercap.chefduplacard.application.pantry_staples.get_pantry_staples_state.GetPantryStaplesStateRequestModel;
import io.github.oliviercap.chefduplacard.application.pantry_staples.get_pantry_staples_state.ports.IGetPantryStaplesStateInputPort;
import io.github.oliviercap.chefduplacard.application.pantry_staples.get_pantry_staples_state.ports.IGetPantryStaplesStateOutputPort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetPantryStaplesStateController {

    private final IGetPantryStaplesStateInputPort inputPort;
    private final IGetPantryStaplesStateOutputPort outputPort;

    public GetPantryStaplesStateController(
            IGetPantryStaplesStateInputPort inputPort,
            IGetPantryStaplesStateOutputPort outputPort
    ) {
        this.inputPort = inputPort;
        this.outputPort = outputPort;
    }

    @GetMapping("/api/getpantrystaplesstate")
    public GetPantryStaplesStateViewModel getPantryStaplesState(
            @RequestParam Long stockId,
            @RequestParam Long pantryStapleId
    ) {

        inputPort.execute(new GetPantryStaplesStateRequestModel(stockId, pantryStapleId));

        return outputPort.getViewModel();
    }
}
