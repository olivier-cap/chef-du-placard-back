package io.github.oliviercap.chefduplacard.application.unit.get_all_units;

import io.github.oliviercap.chefduplacard.application.htttpresponse.UnitResponse;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IUnitRepository;
import io.github.oliviercap.chefduplacard.application.unit.get_all_units.ports.IGetAllUnitsInputPort;
import io.github.oliviercap.chefduplacard.application.unit.get_all_units.ports.IGetAllUnitsOutputPort;
import io.github.oliviercap.chefduplacard.domain.unit.Unit;

import java.util.List;

public class GetAllUnitsUseCase implements IGetAllUnitsInputPort {

    private final IUnitRepository unitRepository;
    private final IGetAllUnitsOutputPort outputPort;

    public GetAllUnitsUseCase(IUnitRepository unitRepository,
                              IGetAllUnitsOutputPort outputPort
    ) {
        this.unitRepository = unitRepository;
        this.outputPort = outputPort;
    }


    @Override
    public void execute(GetAllUnitsRequestModel requestModel) {
        List<Unit> units = getAllUnits();

        outputPort.displayUnits(new GetAllUnitsResponseModel(
                units.stream().
                        map(UnitResponse::from)
                        .toList()
        ));
    }

    private List<Unit> getAllUnits() {
        return unitRepository.findAll();
    }
}
