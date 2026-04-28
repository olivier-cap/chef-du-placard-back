package io.github.oliviercap.chefduplacard.adapters.persistence.converter;

import io.github.oliviercap.chefduplacard.adapters.persistence.dto.UnitDTO;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UnitJpa;
import org.springframework.stereotype.Component;

@Component
public class UnitJpaToDtoConverter implements IUnitJpaToDtoConverter{
    public UnitJpaToDtoConverter() {
    }

    public UnitDTO toDTO(UnitJpa unitJpa) {

        return new UnitDTO(unitJpa.getName(), unitJpa.getSymbol());
    }
}
