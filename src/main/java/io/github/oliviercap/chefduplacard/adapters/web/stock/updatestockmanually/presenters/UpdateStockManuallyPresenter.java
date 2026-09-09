package io.github.oliviercap.chefduplacard.adapters.web.stock.updatestockmanually.presenters;

import io.github.oliviercap.chefduplacard.adapters.web.stock.updatestockmanually.UpdateStockManuallyViewModel;
import io.github.oliviercap.chefduplacard.application.stock.updatestockmanually.UpdateStockManuallyResponseModel;
import io.github.oliviercap.chefduplacard.application.stock.updatestockmanually.port.IUpdateStockManuallyOutputPort;
import org.springframework.stereotype.Component;

@Component
public class UpdateStockManuallyPresenter implements IUpdateStockManuallyOutputPort {

    private UpdateStockManuallyViewModel viewModel;

    @Override
    public void present(UpdateStockManuallyResponseModel responseModel) {
           viewModel = new UpdateStockManuallyViewModel(
                   responseModel.stockSave(),
                   responseModel.responseMessage()
           );
    }

    public UpdateStockManuallyViewModel getViewModel() {
        return viewModel;
    }
}
