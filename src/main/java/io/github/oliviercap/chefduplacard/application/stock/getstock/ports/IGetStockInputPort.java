package io.github.oliviercap.chefduplacard.application.stock.getstock.ports;

import io.github.oliviercap.chefduplacard.application.stock.getstock.GetStockRequestModel;

public interface IGetStockInputPort {

    void execute(GetStockRequestModel requestModel);
}
