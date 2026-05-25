package io.github.oliviercap.chefduplacard.adapters.web.updatestock.controllers;

import io.github.oliviercap.chefduplacard.adapters.web.updatestock.UpdateStockViewModel;
import io.github.oliviercap.chefduplacard.application.updatestock.UpdateStockRequestModel;
import io.github.oliviercap.chefduplacard.application.updatestock.port.IUpdateStockInputPort;
import io.github.oliviercap.chefduplacard.application.updatestock.port.IUpdateStockOutputPort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateStockController {

    private final IUpdateStockInputPort inputPort;
    private final IUpdateStockOutputPort outputPort;

    public UpdateStockController(IUpdateStockInputPort inputPort,
                                 IUpdateStockOutputPort outputPort) {
        this.inputPort = inputPort;
        this.outputPort = outputPort;
    }


    @GetMapping("/updateStock")
    public UpdateStockViewModel updateStock(
            @RequestParam String recipeName,
            @RequestParam int nbPeople,
            @RequestParam String stockName
    ) {
        inputPort.execute(new UpdateStockRequestModel(
                recipeName,
                nbPeople,
                stockName
        ));

        return outputPort.getViewModel();
    }
}
