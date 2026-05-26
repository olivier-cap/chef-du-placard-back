package io.github.oliviercap.chefduplacard.application.getstock;

import io.github.oliviercap.chefduplacard.application.dto.StockResponse;
import io.github.oliviercap.chefduplacard.application.getstock.ports.IGetStockInputPort;
import io.github.oliviercap.chefduplacard.application.getstock.ports.IGetStockOutputPort;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IStockRepository;
import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import io.github.oliviercap.chefduplacard.domain.stock.Stock;

import java.util.Optional;

/**
 * Access to the stock
 */
public class GetStockUseCase implements IGetStockInputPort {

    private final IStockRepository stockRepository;
    private final IGetStockOutputPort outputPort;

    public GetStockUseCase(IStockRepository stockRepository,
                           IGetStockOutputPort outputPort) {
        this.stockRepository = stockRepository;
        this.outputPort = outputPort;
    }


    @Override
    public void execute(GetStockRequestModel requestModel) {
        if(requestModel.stockName().isBlank()) {
            throw new DomainException("stock name must not be null");
        }

        Stock stock = getStock(requestModel.stockName());
        outputPort.displayStock(
                new GetStockResponseModel(StockResponse.from(stock))
        );
    }

    private Stock getStock(String stockName) {
        Optional<Stock> stockOptional = stockRepository.findByName(stockName);
        return stockOptional.orElseThrow();
    }
}
