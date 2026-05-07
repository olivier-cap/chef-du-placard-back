package io.github.oliviercap.chefduplacard.adapters.persistence.mapper.unit;

import io.github.oliviercap.chefduplacard.adapters.persistence.dto.UnitDTO;
import io.github.oliviercap.chefduplacard.domain.unit.Unit;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UnitMapper implements IUnitMapper{
    public UnitMapper() {
    }

    public Unit toDomain(UnitDTO unitDTO) {
        Objects.requireNonNull(unitDTO, "unitDTO must not be null");

        return new Unit(unitDTO.name(), unitDTO.symbol());
    }
}
