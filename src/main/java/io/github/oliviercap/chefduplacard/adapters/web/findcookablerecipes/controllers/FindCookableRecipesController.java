package io.github.oliviercap.chefduplacard.adapters.web.findcookablerecipes.controllers;

import io.github.oliviercap.chefduplacard.adapters.web.findcookablerecipes.FindCookableRecipesViewModel;
import io.github.oliviercap.chefduplacard.adapters.web.findcookablerecipes.presenters.FindCookableRecipesPresenter;
import io.github.oliviercap.chefduplacard.application.cookablerecipes.FindCookableRecipesRequestModel;
import io.github.oliviercap.chefduplacard.application.cookablerecipes.ports.IFindCookableRecipesInputPort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FindCookableRecipesController {

    private final IFindCookableRecipesInputPort inputPort;
    private final FindCookableRecipesPresenter presenter;

    public FindCookableRecipesController(
            IFindCookableRecipesInputPort inputPort,
            FindCookableRecipesPresenter presenter
    ) {
        this.inputPort = inputPort;
        this.presenter = presenter;
    }

    @GetMapping("/api/findCookableRecipes")
    public FindCookableRecipesViewModel findCookableRecipes(
            @RequestParam int nbPeople,
            @RequestParam Long stockId
    ) {
        FindCookableRecipesRequestModel requestModel =
                new FindCookableRecipesRequestModel(nbPeople, stockId);

        inputPort.execute(requestModel);

        return presenter.getViewModel();
    }
}