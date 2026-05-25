package io.github.oliviercap.chefduplacard.application.ports.persistence;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UnitJpa;
import io.github.oliviercap.chefduplacard.domain.unit.Unit;

import java.util.List;

public interface IUnitRepository {
    Unit findUnitByName(String name);
    Unit findUnitById(Long id);

    List<UnitJpa> findAllJpa();
}
