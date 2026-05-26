package io.github.oliviercap.chefduplacard.application.getstock.ports;

import io.github.oliviercap.chefduplacard.adapters.web.getstock.GetStockViewModel;
import io.github.oliviercap.chefduplacard.application.getstock.GetStockResponseModel;

public interface IGetStockOutputPort {
    void displayStock(GetStockResponseModel responseModel);

    GetStockViewModel getViewModel();
}
