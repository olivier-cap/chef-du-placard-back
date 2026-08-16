package io.github.oliviercap.chefduplacard.adapters.web.savenewmenu.controllers;

import io.github.oliviercap.chefduplacard.adapters.web.savenewmenu.SaveNewMenuViewModel;
import io.github.oliviercap.chefduplacard.application.savenewmenu.SaveNewMenuRequestModel;
import io.github.oliviercap.chefduplacard.application.savenewmenu.port.ISaveNewMenuInputPort;
import io.github.oliviercap.chefduplacard.application.savenewmenu.port.ISaveNewMenuOutputPort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SaveNewMenuController {

    private final ISaveNewMenuInputPort inputPort;
    private final ISaveNewMenuOutputPort outputPort;

    public SaveNewMenuController(ISaveNewMenuInputPort inputPort,
                                 ISaveNewMenuOutputPort outputPort) {
        this.inputPort = inputPort;
        this.outputPort = outputPort;
    }

    @PostMapping("/api/saveNewMenu")
    public SaveNewMenuViewModel saveNewMenuViewModel(
        @RequestBody SaveNewMenuRequest newMenu
    ){
        inputPort.execute(new SaveNewMenuRequestModel(newMenu));

        return outputPort.getViewModel();
    }
}
