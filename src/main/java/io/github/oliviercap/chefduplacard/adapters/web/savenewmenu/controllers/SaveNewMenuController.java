package io.github.oliviercap.chefduplacard.adapters.web.savenewmenu.controllers;

import io.github.oliviercap.chefduplacard.adapters.web.savenewmenu.SaveNewMenuViewModel;
import io.github.oliviercap.chefduplacard.adapters.web.savenewmenu.UpdateStockRequest;
import io.github.oliviercap.chefduplacard.application.savenewmenu.SaveNewMenuRequestModel;
import io.github.oliviercap.chefduplacard.application.savenewmenu.port.ISaveNewMenuInputPort;
import io.github.oliviercap.chefduplacard.application.savenewmenu.port.ISaveNewMenuOutputPort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/saveNewMenu")
    public SaveNewMenuViewModel saveNewMenuViewModel(
        @RequestParam UpdateStockRequest newMenus
    ){
        inputPort.execute(new SaveNewMenuRequestModel(newMenus));

        return outputPort.getViewModel();
    }
}
