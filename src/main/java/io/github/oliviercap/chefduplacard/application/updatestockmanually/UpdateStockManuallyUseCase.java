package io.github.oliviercap.chefduplacard.application.updatestockmanually;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.synchronizer.stock.IStockJpaSynchronizer;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IAlimentRepository;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IStockRepository;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IUnitRepository;
import io.github.oliviercap.chefduplacard.application.updatestockmanually.port.IUpdateStockManuallyInputPort;
import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import io.github.oliviercap.chefduplacard.domain.food.Aliment;
import io.github.oliviercap.chefduplacard.domain.stock.Stock;
import io.github.oliviercap.chefduplacard.domain.stock.StockLine;
import io.github.oliviercap.chefduplacard.domain.unit.Unit;

import java.util.List;

public class UpdateStockManuallyUseCase implements IUpdateStockManuallyInputPort {

    private final IStockRepository stockRepository;
    private final IAlimentRepository alimentRepository;
    private final IUnitRepository unitRepository;
    private final IStockJpaSynchronizer stockJpaSynchronizer;
    private boolean stockSaved;

    public UpdateStockManuallyUseCase(IStockRepository stockRepository,
                                      IAlimentRepository alimentRepository,
                                      IUnitRepository unitRepository,
                                      IStockJpaSynchronizer stockJpaSynchronizer) {
        this.stockRepository = stockRepository;
        this.alimentRepository = alimentRepository;
        this.unitRepository = unitRepository;
        this.stockJpaSynchronizer = stockJpaSynchronizer;
    }

    @Override
    public void execute(UpdateStockManuallyRequestModel requestModel) {
        updateStock(requestModel.stockName(), requestModel.updateStockAliments());

        outputPort.present(new UpdateStockManuallyResponsetModel(stockSaved));
    }

    /**
     * Construis le stock mis à jour avec les informations provenant de l'utilisateur sur l'état du stock
    */
    private void updateStock(String stockName, List<UpdateStockManuallyRequestModel.UpdateStockAliment> updateStockAliments) {

        Stock newStock = new Stock(stockName, List.of());

        for(UpdateStockManuallyRequestModel.UpdateStockAliment newLine : updateStockAliments) {
            //search for aliment
            Aliment aliment = alimentRepository.findAlimentByName(newLine.alimentName())
                    .orElseThrow(() -> new DomainException("Aliment not found in database \n" + newLine.alimentName()));

            //search for unit
            Unit unit = unitRepository.findUnitByName(newLine.unitName())
                    .orElseThrow(() -> new DomainException("Unit not found in database \n" + newLine.unitName()));

            StockLine newStockLine = new StockLine(newLine.newQuantity(), aliment, unit);
            newStock.addNewStockLine(newStockLine);
        }

        try {
            stockRepository.save(newStock);
        } catch (Exception e) {
            stockSaved = false;
            throw new DomainException("Impossible to save new stock state \n",e);
        }

        stockSaved = true;
    }
}
