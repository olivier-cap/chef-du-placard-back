package io.github.oliviercap.chefduplacard.adapters.web.pantry_staples.find_by_user.controllers;

import io.github.oliviercap.chefduplacard.adapters.web.pantry_staples.find_by_user.FindByUserPantryStaplesViewModel;
import io.github.oliviercap.chefduplacard.application.pantry_staples.find_by_user.FindByUserPantryStaplesRequestModel;
import io.github.oliviercap.chefduplacard.application.pantry_staples.find_by_user.ports.IFindByUserPantryStaplesInputPort;
import io.github.oliviercap.chefduplacard.application.pantry_staples.find_by_user.ports.IFindByUserPantryStaplesOutputPort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FindByUserPantryStaplesController {

    private final IFindByUserPantryStaplesInputPort inputPort;
    private final IFindByUserPantryStaplesOutputPort outputPort;


    public FindByUserPantryStaplesController(
            IFindByUserPantryStaplesInputPort inputPort,
            IFindByUserPantryStaplesOutputPort outputPort) {
        this.inputPort = inputPort;
        this.outputPort = outputPort;
    }

    @GetMapping("/api/pantrystaplesuser/")
    public FindByUserPantryStaplesViewModel findByIdPantryStaples(
        @PathVariable Long userId
    )
    {
        inputPort.execute(new FindByUserPantryStaplesRequestModel(userId));

        return outputPort.getViewModel();
    }
}
