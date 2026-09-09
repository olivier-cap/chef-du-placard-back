package io.github.oliviercap.chefduplacard.adapters.web.recipes.createnewrecipe.controllers;

import io.github.oliviercap.chefduplacard.adapters.web.recipes.createnewrecipe.CreateNewRecipeViewModel;
import io.github.oliviercap.chefduplacard.application.recipes.createnewrecipe.CreateNewRecipeRequestModel;
import io.github.oliviercap.chefduplacard.application.recipes.createnewrecipe.IngredientsData;
import io.github.oliviercap.chefduplacard.application.recipes.createnewrecipe.ports.ICreateNewRecipeInputPort;
import io.github.oliviercap.chefduplacard.application.recipes.createnewrecipe.ports.ICreateNewRecipeOutputPort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.List;

@RestController
public class CreateNewRecipeController {

    private CreateNewRecipeRequestModel requestModel;
    private final ICreateNewRecipeInputPort inputPort;
    private final ICreateNewRecipeOutputPort outputPort;


    public CreateNewRecipeController(
            ICreateNewRecipeInputPort inputPort,
            ICreateNewRecipeOutputPort outputPort) {
        this.inputPort = inputPort;
        this.outputPort = outputPort;
    }

    @PostMapping("/api/createNewRecipe")
    public CreateNewRecipeViewModel createNewRecipe(
            @RequestParam  String name,
            @RequestParam String instructions,
            @RequestParam Duration duration,
            @RequestParam String difficulty,
            @RequestParam List<IngredientsData> ingredients
    ) {

        requestModel = new CreateNewRecipeRequestModel(
                name,
                instructions,
                duration,
                difficulty,
                ingredients
        );

        inputPort.execute(requestModel);

        return  outputPort.getViewModel();
    }
}
