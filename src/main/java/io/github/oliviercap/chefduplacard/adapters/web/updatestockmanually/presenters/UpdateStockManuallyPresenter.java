package io.github.oliviercap.chefduplacard.adapters.web.updatestockmanually.presenters;

import io.github.oliviercap.chefduplacard.adapters.web.updatestockmanually.UpdateStockManuallyViewModel;
import io.github.oliviercap.chefduplacard.application.updatestockmanually.UpdateStockManuallyResponseModel;
import io.github.oliviercap.chefduplacard.application.updatestockmanually.port.IUpdateStockManuallyOutputPort;
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
