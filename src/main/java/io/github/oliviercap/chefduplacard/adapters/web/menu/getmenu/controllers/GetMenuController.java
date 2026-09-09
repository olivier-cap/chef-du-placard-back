package io.github.oliviercap.chefduplacard.adapters.web.menu.getmenu.controllers;

import io.github.oliviercap.chefduplacard.adapters.web.menu.getmenu.GetMenuViewModel;
import io.github.oliviercap.chefduplacard.application.menu.getmenu.GetMenuRequestModel;
import io.github.oliviercap.chefduplacard.application.menu.getmenu.ports.IGetMenuInputPort;
import io.github.oliviercap.chefduplacard.application.menu.getmenu.ports.IGetMenuOutputPort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetMenuController {

    private final IGetMenuInputPort inputPort;
    private final IGetMenuOutputPort outputPort;

    public GetMenuController(IGetMenuInputPort inputPort, IGetMenuOutputPort outputPort) {
        this.inputPort = inputPort;
        this.outputPort = outputPort;
    }

    @GetMapping("/api/getmenu")
    public GetMenuViewModel getMenu(
            @RequestParam Long menuId
    ) {
        inputPort.execute(new GetMenuRequestModel(menuId));

        return outputPort.getViewModel();
    }
}
