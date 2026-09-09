package io.github.oliviercap.chefduplacard.adapters.web.menu.cookablemenus.controllers;

import io.github.oliviercap.chefduplacard.adapters.web.menu.cookablemenus.CookableMenusViewModel;
import io.github.oliviercap.chefduplacard.adapters.web.menu.cookablemenus.presenters.CookableMenusPresenter;
import io.github.oliviercap.chefduplacard.application.menu.cookablemenus.CookableMenusRequestModel;
import io.github.oliviercap.chefduplacard.application.menu.cookablemenus.ports.ICookableMenusInputPort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CookableMenusController {

    private final ICookableMenusInputPort inputPort;
    private final CookableMenusPresenter presenter;

    public CookableMenusController(
            ICookableMenusInputPort inputPort,
            CookableMenusPresenter presenter
    ) {
        this.inputPort = inputPort;
        this.presenter = presenter;
    }

    @PostMapping("/api/cookableMenus")
    public CookableMenusViewModel cookableMenus(
        @RequestBody CookableMenusRequest request
    ) {
        CookableMenusRequestModel requestModel =
                new CookableMenusRequestModel(request.stockId(), request.nbMeal(), request.nbPeople(), List.of());

        inputPort.execute(requestModel);

        return presenter.getViewModel();
    }
}