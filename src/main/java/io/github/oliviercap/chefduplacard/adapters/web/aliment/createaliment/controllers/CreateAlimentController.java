package io.github.oliviercap.chefduplacard.adapters.web.aliment.createaliment.controllers;

import io.github.oliviercap.chefduplacard.adapters.web.aliment.createaliment.CreateAlimentViewModel;
import io.github.oliviercap.chefduplacard.application.aliment.createaliment.CreateAlimentRequestModel;
import io.github.oliviercap.chefduplacard.application.aliment.createaliment.ports.ICreateAlimentInputPort;
import io.github.oliviercap.chefduplacard.application.aliment.createaliment.ports.ICreateAlimentOutputPort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateAlimentController {

    private final ICreateAlimentInputPort inputPort;
    private final ICreateAlimentOutputPort outputPort;

    public CreateAlimentController(ICreateAlimentInputPort inputPort,
                                   ICreateAlimentOutputPort outputPort) {
        this.inputPort = inputPort;
        this.outputPort = outputPort;
    }


    @PostMapping("/api/createAliment")
    public CreateAlimentViewModel createAliment(
            @RequestParam String alimentName,
            @RequestParam String alimentDescription,
            @RequestParam boolean isActive
    ){

        inputPort.execute(new CreateAlimentRequestModel(alimentName, alimentDescription, isActive));

        return outputPort.getViewModel();
    }
}
