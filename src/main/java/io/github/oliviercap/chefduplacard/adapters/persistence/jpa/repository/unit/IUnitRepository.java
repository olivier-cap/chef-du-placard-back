package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.unit;

import io.github.oliviercap.chefduplacard.domain.unit.Unit;

public interface IUnitRepository {
    Unit findUnitByName(String name);
    Unit findUnitById(Long id);
}
