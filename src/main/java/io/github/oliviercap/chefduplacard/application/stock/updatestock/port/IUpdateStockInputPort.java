package io.github.oliviercap.chefduplacard.application.stock.updatestock.port;

import io.github.oliviercap.chefduplacard.application.stock.updatestock.UpdateStockRequestModel;

public interface IUpdateStockInputPort {
    void execute(UpdateStockRequestModel requestModel);
}
