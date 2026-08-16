package io.github.oliviercap.chefduplacard.application.updatestockmanually;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.stock.UpdateStockDTO;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IAlimentRepository;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IStockRepository;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IUnitRepository;
import io.github.oliviercap.chefduplacard.application.updatestockmanually.port.IUpdateStockManuallyInputPort;
import io.github.oliviercap.chefduplacard.application.updatestockmanually.port.IUpdateStockManuallyOutputPort;
import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;

public class UpdateStockManuallyUseCase implements IUpdateStockManuallyInputPort {

    private final IStockRepository stockRepository;
    private final IAlimentRepository alimentRepository;
    private final IUnitRepository unitRepository;
    private final IUpdateStockManuallyOutputPort outputPort;
    private boolean stockSaved;

    public UpdateStockManuallyUseCase(IStockRepository stockRepository,
                                      IAlimentRepository alimentRepository,
                                      IUnitRepository unitRepository,
                                      IUpdateStockManuallyOutputPort outputPort) {
        this.stockRepository = stockRepository;
        this.alimentRepository = alimentRepository;
        this.unitRepository = unitRepository;
        this.outputPort = outputPort;
    }

    @Override
    public void execute(UpdateStockManuallyRequestModel requestModel) {
        updateStock(requestModel);

        String reponseMessage = stockSaved ? "Stock saved" : "Problem occurred during stock save";
        outputPort.present(new UpdateStockManuallyResponseModel(stockSaved, reponseMessage));
    }

    /**
     * Construis le stock mis à jour avec les informations provenant de l'utilisateur sur l'état du stock
    */
    private void updateStock(UpdateStockManuallyRequestModel request) {

        try {
            stockRepository.updateStock(
                    new UpdateStockDTO(
                            request.stockId(),
                            request.updateStockAliments().stream()
                                    .map(sa ->
                                            new UpdateStockDTO.NewQuantities(
                                                    sa.stockLineId(),
                                                    sa.newQuantity(),
                                                    sa.unitId()
                                            )
                                    ).toList()
                    )
            );
        } catch (Exception e) {
            stockSaved = false;
            throw new DomainException("Impossible to save new stock state \n",e);
        }

        stockSaved = true;
    }
}
