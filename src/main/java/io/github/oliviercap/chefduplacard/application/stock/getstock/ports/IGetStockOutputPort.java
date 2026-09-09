package io.github.oliviercap.chefduplacard.application.stock.getstock.ports;

import io.github.oliviercap.chefduplacard.adapters.web.stock.getstock.GetStockViewModel;
import io.github.oliviercap.chefduplacard.application.stock.getstock.GetStockResponseModel;

public interface IGetStockOutputPort {
    void displayStock(GetStockResponseModel responseModel);

    GetStockViewModel getViewModel();
}
