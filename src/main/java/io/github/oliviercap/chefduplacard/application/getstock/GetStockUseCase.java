package io.github.oliviercap.chefduplacard.application.getstock;

import io.github.oliviercap.chefduplacard.application.getstock.ports.IGetStockInputPort;
import io.github.oliviercap.chefduplacard.application.getstock.ports.IGetStockOutputPort;
import io.github.oliviercap.chefduplacard.application.ports.query.IGetStockViewQuery;
import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;

import java.util.List;

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
        if(requestModel.stockId() == null) {
            throw new DomainException("stockid must not be null");
        }

        outputPort.displayStock(
                new GetStockResponseModel(getStock(requestModel.stockId()))
        );
    }

    private List<GetStockQuery> getStock(Long stockId) {
        return getStockViewQuery.getStockQuery(stockId);
    }
}
