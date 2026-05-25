package io.github.oliviercap.chefduplacard.application.updatestock.port;

import io.github.oliviercap.chefduplacard.application.updatestock.UpdateStockRequestModel;

public interface IUpdateStockInputPort {
    void execute(UpdateStockRequestModel requestModel);
}
