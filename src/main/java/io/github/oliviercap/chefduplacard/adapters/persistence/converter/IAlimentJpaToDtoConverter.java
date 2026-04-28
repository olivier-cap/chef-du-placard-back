package io.github.oliviercap.chefduplacard.adapters.persistence.converter;

import io.github.oliviercap.chefduplacard.adapters.persistence.dto.AlimentDTO;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.AlimentJpa;

public interface IAlimentJpaToDtoConverter {
    AlimentDTO toDTO(AlimentJpa alimentJpa);
}
