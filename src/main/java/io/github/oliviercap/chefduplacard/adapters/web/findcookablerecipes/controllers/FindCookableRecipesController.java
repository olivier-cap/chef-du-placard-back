package io.github.oliviercap.chefduplacard.adapters.web.findcookablerecipes.controllers;

import io.github.oliviercap.chefduplacard.adapters.web.findcookablerecipes.FindCookableRecipesRequestModel;
import io.github.oliviercap.chefduplacard.adapters.web.findcookablerecipes.FindCookableRecipesResponseModel;
import io.github.oliviercap.chefduplacard.application.cookablerecipes.IFindCookableRecipesInputPort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FindCookableRecipesController {

    private final IFindCookableRecipesInputPort inputPort;

    public FindCookableRecipesController(IFindCookableRecipesInputPort inputPort) {
        this.inputPort = inputPort;
    }

    //listen http /findCookableRecipes  ==  CHANGER SI BESOIN !!! POTENTIELLEMENT TEMPORAIRE
    //get data
    //create requestModel
    //sendRequestModelToUseCase

    @GetMapping("/findCookableRecipes")
    public FindCookableRecipesResponseModel findCookableRecipes(
            @RequestParam int nbPeople,
            @RequestParam String stock) {

        return inputPort.execute(
                new FindCookableRecipesRequestModel(nbPeople, stock)
        );
    }


}
