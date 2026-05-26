package io.github.oliviercap.chefduplacard.application.updatestock.port;

import io.github.oliviercap.chefduplacard.adapters.web.updatestock.UpdateStockViewModel;
import io.github.oliviercap.chefduplacard.application.updatestock.UpdateStockResponseModel;

public interface IUpdateStockOutputPort {
    void updateStockResponse(UpdateStockResponseModel updateStockResponseModel);
    UpdateStockViewModel getViewModel();
}
