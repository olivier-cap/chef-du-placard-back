package io.github.oliviercap.chefduplacard.adapters.web.getstock.presenters;

import io.github.oliviercap.chefduplacard.adapters.web.getstock.GetStockViewModel;
import io.github.oliviercap.chefduplacard.application.dto.StockLineResponse;
import io.github.oliviercap.chefduplacard.application.getstock.GetStockResponseModel;
import io.github.oliviercap.chefduplacard.application.getstock.ports.IGetStockOutputPort;
import org.springframework.stereotype.Component;

@Component
public class GetStockPresenter implements IGetStockOutputPort {
    private GetStockViewModel viewModel;

    @Override
    public void displayStock(GetStockResponseModel responseModel) {
        viewModel = new GetStockViewModel(responseModel.stockResponse().stockLines().stream()
                .map(this::toStockLineViewModel)
                .toList()
        );
    }

    @Override
    public GetStockViewModel getViewModel() {
        return viewModel;
    }

    private GetStockViewModel.StockLineViewModel toStockLineViewModel(StockLineResponse lineResponse) {
        return new GetStockViewModel.StockLineViewModel(
                lineResponse.quantity(),
                lineResponse.aliment().name(),
                lineResponse.unit().symbol()
        );
    }
}
