package io.github.oliviercap.chefduplacard.application.getstock;

import io.github.oliviercap.chefduplacard.application.htttpresponse.StockResponse;
import io.github.oliviercap.chefduplacard.application.getstock.ports.IGetStockInputPort;
import io.github.oliviercap.chefduplacard.application.getstock.ports.IGetStockOutputPort;
import io.github.oliviercap.chefduplacard.application.ports.query.IGetStockViewQuery;
import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import io.github.oliviercap.chefduplacard.domain.stock.Stock;

import java.util.List;
import java.util.Optional;

/**
 * Access to the stock
 */
public class GetStockUseCase implements IGetStockInputPort {

    private final IGetStockViewQuery getStockViewQuery;
    private final IGetStockOutputPort outputPort;

    public GetStockUseCase(IGetStockViewQuery getStockViewQuery,
                           IGetStockOutputPort outputPort) {
        this.getStockViewQuery = getStockViewQuery;
        this.outputPort = outputPort;
    }


    @Override
    public void execute(GetStockRequestModel requestModel) {
        if(requestModel.stockName().isBlank()) {
            throw new DomainException("stock name must not be null");
        }

        outputPort.displayStock(
                new GetStockResponseModel(getStock(requestModel.stockName()))
        );
    }

    private List<GetStockQuery> getStock(String stockName) {
        return getStockViewQuery.getStockQuery(stockName);
    }
}
