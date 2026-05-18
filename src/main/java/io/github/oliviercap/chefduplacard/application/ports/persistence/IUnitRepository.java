package io.github.oliviercap.chefduplacard.application.ports.persistence;

import io.github.oliviercap.chefduplacard.domain.unit.Unit;

public interface IUnitRepository {
    Unit findUnitByName(String name);
    Unit findUnitById(Long id);
}
