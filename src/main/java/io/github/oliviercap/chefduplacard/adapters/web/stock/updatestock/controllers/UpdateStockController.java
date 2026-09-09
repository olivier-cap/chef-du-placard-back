package io.github.oliviercap.chefduplacard.adapters.web.stock.updatestock.controllers;

import io.github.oliviercap.chefduplacard.adapters.web.stock.updatestock.UpdateStockViewModel;
import io.github.oliviercap.chefduplacard.application.stock.updatestock.UpdateStockRequestModel;
import io.github.oliviercap.chefduplacard.application.stock.updatestock.port.IUpdateStockInputPort;
import io.github.oliviercap.chefduplacard.application.stock.updatestock.port.IUpdateStockOutputPort;
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


    @GetMapping("/api/updateStock")
    public UpdateStockViewModel updateStock(
            @RequestParam Long recipeId,
            @RequestParam int nbPeople,
            @RequestParam Long stockId,
            @RequestParam Long userId
    ) {
        inputPort.execute(new UpdateStockRequestModel(
                stockId,
                recipeId,
                nbPeople,
                userId
        ));

        return outputPort.getViewModel();
    }
}
