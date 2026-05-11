package io.github.oliviercap.chefduplacard.application.converter.unitresponse;

import io.github.oliviercap.chefduplacard.application.dto.UnitResponse;
import io.github.oliviercap.chefduplacard.domain.unit.Unit;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UnitToUnitResponse implements IUnitToUnitResponse{
    public UnitResponse toDTO(Unit unit) {
        Objects.requireNonNull(unit, "unit must not be null");
        return new UnitResponse(unit.getName(), unit.getSymbol());
    }
}
