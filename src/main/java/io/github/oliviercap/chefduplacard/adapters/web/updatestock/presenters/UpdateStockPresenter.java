package io.github.oliviercap.chefduplacard.adapters.web.updatestock.presenters;

import io.github.oliviercap.chefduplacard.adapters.web.updatestock.UpdateStockViewModel;
import io.github.oliviercap.chefduplacard.application.updatestock.UpdateStockResponseModel;
import io.github.oliviercap.chefduplacard.application.updatestock.port.IUpdateStockOutputPort;
import org.springframework.stereotype.Component;

@Component
public class UpdateStockPresenter implements IUpdateStockOutputPort {
    private UpdateStockViewModel viewModel;

    @Override
    public void updateStockResponse(UpdateStockResponseModel updateStockResponseModel) {
        viewModel = new UpdateStockViewModel(updateStockResponseModel.sufficientStock(), updateStockResponseModel.responseMessage());
    }

    @Override
    public UpdateStockViewModel getViewModel() {
        return viewModel;
    }
}
