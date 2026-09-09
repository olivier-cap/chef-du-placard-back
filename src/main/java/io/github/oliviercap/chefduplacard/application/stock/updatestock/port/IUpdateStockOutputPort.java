package io.github.oliviercap.chefduplacard.application.stock.updatestock.port;

import io.github.oliviercap.chefduplacard.adapters.web.stock.updatestock.UpdateStockViewModel;
import io.github.oliviercap.chefduplacard.application.stock.updatestock.UpdateStockResponseModel;

public interface IUpdateStockOutputPort {
    void updateStockResponse(UpdateStockResponseModel updateStockResponseModel);
    UpdateStockViewModel getViewModel();
}
