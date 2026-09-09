package io.github.oliviercap.chefduplacard.application.pantry_staples.get_pantry_staples_state;

import io.github.oliviercap.chefduplacard.application.htttpresponse.PantryStaplesLineResponse;
import io.github.oliviercap.chefduplacard.application.pantry_staples.get_pantry_staples_state.ports.IGetPantryStaplesStateInputPort;
import io.github.oliviercap.chefduplacard.application.pantry_staples.get_pantry_staples_state.ports.IGetPantryStaplesStateOutputPort;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IPantryStaplesRepository;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IStockRepository;
import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import io.github.oliviercap.chefduplacard.domain.pantry_staples.PantryStaples;
import io.github.oliviercap.chefduplacard.domain.pantry_staples.PantryStaplesLine;
import io.github.oliviercap.chefduplacard.domain.stock.Stock;
import io.github.oliviercap.chefduplacard.domain.stock.StockLine;

import java.util.List;

public class GetPantryStaplesStateUseCase implements IGetPantryStaplesStateInputPort {

    private final IStockRepository stockRepository;
    private final IPantryStaplesRepository pantryStaplesRepository;
    private final IGetPantryStaplesStateOutputPort outputPort;

    public GetPantryStaplesStateUseCase(
            IStockRepository stockRepository,
            IPantryStaplesRepository pantryStaplesRepository,
            IGetPantryStaplesStateOutputPort outputPort
    ) {
        this.stockRepository = stockRepository;
        this.pantryStaplesRepository = pantryStaplesRepository;
        this.outputPort = outputPort;
    }


    @Override
    public void execute(GetPantryStaplesStateRequestModel requestModel) {

        outputPort.displayPantryStaplesState(
                getPantryStapleState(requestModel.stockId(), requestModel.pantryStaplesId())
        );
    }


    private GetPantryStaplesStateResponseModel getPantryStapleState(Long stockId, Long pantryStaplesId) {

        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new DomainException("stock not found"));

        PantryStaples pantryStaples = pantryStaplesRepository.findBydId(pantryStaplesId)
                .orElseThrow(() -> new DomainException("pantry staples not found"));

        List<StockLine> stockLines = stock.getStockAliments(
                pantryStaples.getPantryStaplesLineMap().values()
                        .stream()
                        .map(PantryStaplesLine::getAliment)
                        .toList()
        );


        //Création response model : lignes du fond de placard, quantité actuelle & boolean quantité suffisante (oui = true)?
        GetPantryStaplesStateResponseModel responseModel = new GetPantryStaplesStateResponseModel(
                stockLines.stream().map(
                        sl -> new GetPantryStaplesStateResponseModel.StapleStateLine(
                                PantryStaplesLineResponse.from(pantryStaples.getPantryStaplesLineMap().get(sl.getAliment())),
                                sl.getQuantity(),
                                sl.getQuantity().compareTo(pantryStaples.getPantryStaplesLineMap().get(sl.getAliment()).getQuantity()) >= 0
                        )
                ).toList()
        );


        return responseModel;
    }
}
