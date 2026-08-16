package io.github.oliviercap.chefduplacard.adapters.web.getstock.presenters;

import io.github.oliviercap.chefduplacard.adapters.web.getstock.GetStockViewModel;
import io.github.oliviercap.chefduplacard.application.getstock.GetStockQuery;
import io.github.oliviercap.chefduplacard.application.getstock.GetStockResponseModel;
import io.github.oliviercap.chefduplacard.application.getstock.ports.IGetStockOutputPort;
import org.springframework.stereotype.Component;

@Component
public class GetStockPresenter implements IGetStockOutputPort {
    private GetStockViewModel viewModel;

    @Override
    public void displayStock(GetStockResponseModel responseModel) {
        viewModel = new GetStockViewModel(
                responseModel.getStockQueryList().stream()
                .map(this::toStockLineViewModel)
                .toList()
        );
    }

    @Override
    public GetStockViewModel getViewModel() {
        return viewModel;
    }

    private GetStockViewModel.StockLineViewModel toStockLineViewModel(GetStockQuery getStockQuery) {
        return new GetStockViewModel.StockLineViewModel(
                getStockQuery.id(),
                getStockQuery.quantity(),
                getStockQuery.alimentName(),
                getStockQuery.unitSymbol()
        );
    }
}
