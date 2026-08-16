package io.github.oliviercap.chefduplacard.adapters.web.modifyaliment.controllers;

import io.github.oliviercap.chefduplacard.adapters.web.modifyaliment.ModifyAlimentViewModel;
import io.github.oliviercap.chefduplacard.application.modifyaliment.ModifyAlimentRequestModel;
import io.github.oliviercap.chefduplacard.application.modifyaliment.ports.IModifyAlimentInputPort;
import io.github.oliviercap.chefduplacard.application.modifyaliment.ports.IModifyAlimentOutputPort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ModifyAlimentController {

    IModifyAlimentInputPort inputPort;
    IModifyAlimentOutputPort outputPort;

    @PostMapping("/api/modfiyAliment")
    public ModifyAlimentViewModel modifyAliment(
        @RequestParam Long alimentId,
        @RequestParam String newAlimentName,
        @RequestParam String alimentDescription
    ){
        inputPort.execute(new ModifyAlimentRequestModel(alimentId, newAlimentName, alimentDescription));

        return outputPort.getViewModel();
    }
}
