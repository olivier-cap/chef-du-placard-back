package io.github.oliviercap.chefduplacard.application.stock.updatestockmanually.port;

import io.github.oliviercap.chefduplacard.application.stock.updatestockmanually.UpdateStockManuallyRequestModel;

public interface IUpdateStockManuallyInputPort {
    void execute(UpdateStockManuallyRequestModel requestModel);
}
