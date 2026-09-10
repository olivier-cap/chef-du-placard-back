package io.github.oliviercap.chefduplacard.adapters.web.pantry_staples.modify_pantry_staples_line.controllers;

import io.github.oliviercap.chefduplacard.adapters.web.pantry_staples.modify_pantry_staples_line.ModifyPantryStaplesLineViewModel;
import io.github.oliviercap.chefduplacard.application.pantry_staples.modify_pantry_staples_line.ModifyPantryStaplesLineRequestModel;
import io.github.oliviercap.chefduplacard.application.pantry_staples.modify_pantry_staples_line.ports.IModifyPantryStaplesLineInputPort;
import io.github.oliviercap.chefduplacard.application.pantry_staples.modify_pantry_staples_line.ports.IModifyPantryStaplesLineOutputPort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ModifyPantryStaplesLineController {

    private final IModifyPantryStaplesLineInputPort inputPort;
    private final IModifyPantryStaplesLineOutputPort outputPort;

    public ModifyPantryStaplesLineController(
            IModifyPantryStaplesLineInputPort inputPort,
            IModifyPantryStaplesLineOutputPort outputPort
    ) {
        this.inputPort = inputPort;
        this.outputPort = outputPort;
    }

    @PostMapping("/api/modifyStaplesLine")
    public ModifyPantryStaplesLineViewModel modifyPantryStaplesLine(
            @RequestBody ModifyPantryStaplesLineRequest request
    ) {
        inputPort.execute(new ModifyPantryStaplesLineRequestModel(
                request.pantryStapleLineId(),
                request.quantity(),
                request.unitId()
        ));

        return outputPort.getViewModel();
    }
}
