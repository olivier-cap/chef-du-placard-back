package io.github.oliviercap.chefduplacard.application.getstock.ports;

import io.github.oliviercap.chefduplacard.application.getstock.GetStockRequestModel;

public interface IGetStockInputPort {

    void execute(GetStockRequestModel requestModel);
}
