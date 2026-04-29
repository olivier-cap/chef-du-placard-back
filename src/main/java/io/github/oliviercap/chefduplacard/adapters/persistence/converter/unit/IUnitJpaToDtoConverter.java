package io.github.oliviercap.chefduplacard.adapters.persistence.converter.unit;

import io.github.oliviercap.chefduplacard.adapters.persistence.dto.UnitDTO;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UnitJpa;

public interface IUnitJpaToDtoConverter {
    UnitDTO toDTO(UnitJpa unitJpa);
}
