package io.github.oliviercap.chefduplacard.adapters.web.getstock.controllers;

import io.github.oliviercap.chefduplacard.adapters.web.getstock.GetStockViewModel;
import io.github.oliviercap.chefduplacard.application.getstock.GetStockRequestModel;
import io.github.oliviercap.chefduplacard.application.getstock.ports.IGetStockInputPort;
import io.github.oliviercap.chefduplacard.application.getstock.ports.IGetStockOutputPort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetStockController {

    private final IGetStockInputPort inputPort;
    private final IGetStockOutputPort outputPort;

    public GetStockController(IGetStockInputPort inputPort,
                              IGetStockOutputPort outputPort) {
        this.inputPort = inputPort;
        this.outputPort = outputPort;
    }

    @GetMapping("/getStock")
    GetStockViewModel getStock(
            @RequestParam String stockName
    ) {
        inputPort.execute(new GetStockRequestModel(stockName));

        return outputPort.getViewModel();
    }
}
