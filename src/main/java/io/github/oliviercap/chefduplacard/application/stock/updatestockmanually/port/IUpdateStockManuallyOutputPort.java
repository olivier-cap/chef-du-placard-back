package io.github.oliviercap.chefduplacard.application.stock.updatestockmanually.port;

import io.github.oliviercap.chefduplacard.adapters.web.stock.updatestockmanually.UpdateStockManuallyViewModel;
import io.github.oliviercap.chefduplacard.application.stock.updatestockmanually.UpdateStockManuallyResponseModel;

public interface IUpdateStockManuallyOutputPort {
    void present(UpdateStockManuallyResponseModel responseModel);

    UpdateStockManuallyViewModel getViewModel();
}
