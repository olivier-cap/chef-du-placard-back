package io.github.oliviercap.chefduplacard.adapters.web.pantry_staples.create_pantry_staples_line.controllers;

import io.github.oliviercap.chefduplacard.adapters.web.pantry_staples.create_pantry_staples_line.CreatePantryStaplesLineViewModel;
import io.github.oliviercap.chefduplacard.application.pantry_staples.create_pantry_staples_line.CreatePantryStaplesLineRequestModel;
import io.github.oliviercap.chefduplacard.application.pantry_staples.create_pantry_staples_line.ports.ICreatePantryStaplesLineInputPort;
import io.github.oliviercap.chefduplacard.application.pantry_staples.create_pantry_staples_line.ports.ICreatePantryStaplesLineOutputPort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreatePantryStaplesLineController {

    private final ICreatePantryStaplesLineInputPort inputPort;
    private final ICreatePantryStaplesLineOutputPort outputPort;

    public CreatePantryStaplesLineController(
            ICreatePantryStaplesLineInputPort inputPort,
            ICreatePantryStaplesLineOutputPort outputPort
    ) {
        this.inputPort = inputPort;
        this.outputPort = outputPort;
    }

    @PostMapping("/api/createpantrystaplesline")
    public CreatePantryStaplesLineViewModel createPantryStaplesLine(
            @RequestBody CreatePantryStaplesLineRequest request
    ) {

        inputPort.execute(new CreatePantryStaplesLineRequestModel(
                request.pantryStaplesId(),
                request.alimentId(),
                request.quantity(),
                request.unitId()
        ));


        return outputPort.getViewModel();
    }
}
