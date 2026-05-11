package io.github.oliviercap.chefduplacard.application.converter.unitresponse;

import io.github.oliviercap.chefduplacard.application.dto.UnitResponse;
import io.github.oliviercap.chefduplacard.domain.unit.Unit;

public interface IUnitToUnitResponse {
    public UnitResponse toDTO(Unit unit);
}
