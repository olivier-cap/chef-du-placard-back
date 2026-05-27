package io.github.oliviercap.chefduplacard.application.updatestockmanually.port;

import io.github.oliviercap.chefduplacard.adapters.web.updatestockmanually.UpdateStockManuallyViewModel;
import io.github.oliviercap.chefduplacard.application.updatestockmanually.UpdateStockManuallyResponseModel;

public interface IUpdateStockManuallyOutputPort {
    void present(UpdateStockManuallyResponseModel responseModel);

    UpdateStockManuallyViewModel getViewModel();
}
