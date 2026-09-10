package io.github.oliviercap.chefduplacard.adapters.web.pantry_staples.delete_pantry_staples_line.controllers;

import io.github.oliviercap.chefduplacard.adapters.web.pantry_staples.delete_pantry_staples_line.DeletePantryStaplesLineViewModel;
import io.github.oliviercap.chefduplacard.application.pantry_staples.delete_pantry_staples_line.DeletePantryStaplesLineRequestModel;
import io.github.oliviercap.chefduplacard.application.pantry_staples.delete_pantry_staples_line.ports.IDeletePantryStaplesLineInputPort;
import io.github.oliviercap.chefduplacard.application.pantry_staples.delete_pantry_staples_line.ports.IDeletePantryStaplesLineOutputPort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeletePantryStaplesLineController {

    private final IDeletePantryStaplesLineInputPort inputPort;
    private final IDeletePantryStaplesLineOutputPort outputPort;


    public DeletePantryStaplesLineController(
            IDeletePantryStaplesLineInputPort inputPort,
            IDeletePantryStaplesLineOutputPort outputPort
    ) {
        this.inputPort = inputPort;
        this.outputPort = outputPort;
    }

    @PostMapping("/api/deletepantrystaplesline")
    public DeletePantryStaplesLineViewModel deletePantryStaplesLine(
            @RequestBody Long pantryStaplesLineId
    ) {
        inputPort.execute(new DeletePantryStaplesLineRequestModel(pantryStaplesLineId));

        return outputPort.getViewModel();
    }
}
