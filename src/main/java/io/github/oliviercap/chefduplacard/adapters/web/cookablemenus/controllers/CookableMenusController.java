package io.github.oliviercap.chefduplacard.adapters.web.cookablemenus.controllers;

import io.github.oliviercap.chefduplacard.adapters.web.cookablemenus.CookableMenusViewModel;
import io.github.oliviercap.chefduplacard.adapters.web.cookablemenus.presenters.CookableMenusPresenter;
import io.github.oliviercap.chefduplacard.application.cookablemenus.CookableMenusRequestModel;
import io.github.oliviercap.chefduplacard.application.cookablemenus.ICookableMenusInputPort;
import io.github.oliviercap.chefduplacard.domain.recipefilters.RecipeFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/cookableMenus")
    public CookableMenusViewModel cookableMenus(
            @RequestParam int nbPeople,
            @RequestParam int nbMeal,
            @RequestParam String stockName,
            @RequestParam(required = false) List<String> filtersName
    ) {
        CookableMenusRequestModel requestModel =
                new CookableMenusRequestModel(stockName, nbMeal, nbPeople, List.of());

        inputPort.execute(requestModel);

        return presenter.getViewModel();
    }
}