package io.github.oliviercap.chefduplacard.adapters.persistence.mapper.unit;

import io.github.oliviercap.chefduplacard.adapters.persistence.dto.UnitDTO;
import io.github.oliviercap.chefduplacard.domain.unit.Unit;

public interface IUnitMapper {
    Unit toDomain(UnitDTO unitDTO);
}
