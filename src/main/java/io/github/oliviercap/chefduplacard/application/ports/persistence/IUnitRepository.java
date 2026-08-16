package io.github.oliviercap.chefduplacard.application.ports.persistence;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UnitJpa;
import io.github.oliviercap.chefduplacard.domain.unit.Unit;

import java.util.List;
import java.util.Optional;

public interface IUnitRepository {
    Optional<Unit> findUnitById(Long id);
    Optional<UnitJpa> findUnitJpaById(Long id);

    List<UnitJpa> findAllJpa();
    Optional<UnitJpa> findJpaById(Long id);
    UnitJpa getReferenceById(Long id);
}
