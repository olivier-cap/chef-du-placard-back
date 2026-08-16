package io.github.oliviercap.chefduplacard.adapters.web.getonerecipe.controllers;

import io.github.oliviercap.chefduplacard.adapters.web.getonerecipe.GetOneRecipeViewModel;
import io.github.oliviercap.chefduplacard.application.getonerecipe.GetOneRecipeRequestModel;
import io.github.oliviercap.chefduplacard.application.getonerecipe.ports.IGetOneRecipeInputPort;
import io.github.oliviercap.chefduplacard.application.getonerecipe.ports.IGetOneRecipeOutputPort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetOneRecipeController {

    private final IGetOneRecipeInputPort inputPort;
    private final IGetOneRecipeOutputPort outputPort;

    public GetOneRecipeController(IGetOneRecipeInputPort inputPort,
                                  IGetOneRecipeOutputPort outputPort) {
        this.inputPort = inputPort;
        this.outputPort = outputPort;
    }

    @GetMapping("/api/getOneRecipe")
    public GetOneRecipeViewModel getOneRecipe(
            @RequestParam String recipeName
    ) {
        inputPort.execute(new GetOneRecipeRequestModel(recipeName));

        return outputPort.getViewModel();
    }
}
