package io.github.oliviercap.chefduplacard.application.converter.stocklineresponse;

import io.github.oliviercap.chefduplacard.application.converter.alimentresponse.IAlimentToAlimentResponse;
import io.github.oliviercap.chefduplacard.application.converter.unitresponse.IUnitToUnitResponse;
import io.github.oliviercap.chefduplacard.application.dto.StockLineResponse;
import io.github.oliviercap.chefduplacard.domain.stock.StockLine;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class StockLineToStockLineResponse implements IStockLineToStockLineResponse {
    private final IAlimentToAlimentResponse alimentToAlimentResponse;
    private final IUnitToUnitResponse unitToUnitResponse;

    public StockLineToStockLineResponse(IAlimentToAlimentResponse alimentToAlimentResponse, IUnitToUnitResponse unitToUnitResponse) {
        this.alimentToAlimentResponse = alimentToAlimentResponse;
        this.unitToUnitResponse = unitToUnitResponse;
    }

    @Override
    public StockLineResponse toDTO(StockLine stockLine) {
        Objects.requireNonNull(stockLine, "stockLine must not be null");
        Objects.requireNonNull(stockLine.getAliment(), "aliment must not be null");
        Objects.requireNonNull(stockLine.getUnit(), "unit must not be null");

        return new StockLineResponse(
                alimentToAlimentResponse.toDTO(stockLine.getAliment()),
                unitToUnitResponse.toDTO(stockLine.getUnit()),
                stockLine.getQuantity()
        );
    }
}
