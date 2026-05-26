package io.github.oliviercap.chefduplacard.application.updatestockmanually.port;

import io.github.oliviercap.chefduplacard.application.updatestockmanually.UpdateStockManuallyRequestModel;

public interface IUpdateStockManuallyInputPort {
    void execute(UpdateStockManuallyRequestModel requestModel);
}
