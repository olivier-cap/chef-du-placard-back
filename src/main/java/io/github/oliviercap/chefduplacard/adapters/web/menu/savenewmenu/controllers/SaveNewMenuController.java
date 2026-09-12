package io.github.oliviercap.chefduplacard.adapters.web.menu.savenewmenu.controllers;

import io.github.oliviercap.chefduplacard.adapters.web.menu.savenewmenu.SaveNewMenuViewModel;
import io.github.oliviercap.chefduplacard.application.menu.savenewmenu.SaveNewMenuRequestModel;
import io.github.oliviercap.chefduplacard.application.menu.savenewmenu.port.ISaveNewMenuInputPort;
import io.github.oliviercap.chefduplacard.application.menu.savenewmenu.port.ISaveNewMenuOutputPort;
import org.springframework.web.bind.annotation.PathVariable;
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

    @PostMapping("/api/savenewmenu")
    public SaveNewMenuViewModel saveNewMenuViewModel(
            @RequestBody SaveNewMenuRequest newMenu
    ) {
        inputPort.execute(
                new SaveNewMenuRequestModel(
                        newMenu.userId(),
                        newMenu.menuName(),
                        newMenu.menuLines().stream().map(
                                line -> new SaveNewMenuRequestModel.MenuLine(
                                        line.nbPerson(),
                                        line.recipeId()
                                )
                        ).toList()
                )
        );

        return outputPort.getViewModel();
    }
}
