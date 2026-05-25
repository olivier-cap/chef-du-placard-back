package io.github.oliviercap.chefduplacard.application.updatestock.port;

import io.github.oliviercap.chefduplacard.adapters.web.updatestock.UpdateStockViewModel;
import io.github.oliviercap.chefduplacard.application.updatestock.UpdateStockResponseModel;

public interface IUpdateStockOutputPort {
    UpdateStockViewModel updateStockResponse(UpdateStockResponseModel updateStockResponseModel);
    UpdateStockViewModel getViewModel();
}
