package io.github.oliviercap.chefduplacard.adapters.web.updatestockmanually.controllers;

import io.github.oliviercap.chefduplacard.adapters.web.updatestockmanually.UpdateStockManuallyViewModel;
import io.github.oliviercap.chefduplacard.application.updatestockmanually.UpdateStockManuallyRequestModel;
import io.github.oliviercap.chefduplacard.application.updatestockmanually.port.IUpdateStockManuallyInputPort;
import io.github.oliviercap.chefduplacard.application.updatestockmanually.port.IUpdateStockManuallyOutputPort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UpdateStockManuallyControler {
    private final IUpdateStockManuallyInputPort inputPort;
    private final IUpdateStockManuallyOutputPort outputPort;

    public UpdateStockManuallyControler(IUpdateStockManuallyInputPort inputPort,
                                        IUpdateStockManuallyOutputPort outputPort) {
        this.inputPort = inputPort;
        this.outputPort = outputPort;
    }

    @PostMapping("/api/updateStockManually")
    public UpdateStockManuallyViewModel updateStockManuallyViewModel(
        @RequestParam Long stockId,
        @RequestParam List<UpdateStockManuallyRequestModel.UpdateStockAliment> updateStockAlimentList
    ) {
        inputPort.execute(
                new UpdateStockManuallyRequestModel(
                        stockId,
                        updateStockAlimentList
                )
        );

        return outputPort.getViewModel();
    }
}
