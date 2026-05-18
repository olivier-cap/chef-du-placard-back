package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.unit;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UnitJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.unit.UnitMapper;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IUnitRepository;
import io.github.oliviercap.chefduplacard.domain.unit.Unit;
import org.springframework.stereotype.Repository;

@Repository
public class UnitRepository implements IUnitRepository {
    private final IUnitJpaRepository unitJpaRepository;
    private final UnitMapper unitMapper;

    public UnitRepository(IUnitJpaRepository unitJpaRepository,
                          UnitMapper unitMapper) {
        this.unitJpaRepository = unitJpaRepository;
        this.unitMapper = unitMapper;
    }


    @Override
    public Unit findUnitByName(String name) {
        UnitJpa unitJpa = unitJpaRepository.findUnitJpaByName(name);
        return unitMapper.toDomain(unitJpa);
    }

    @Override
    public Unit findUnitById(Long id) {
        UnitJpa unitJpa = unitJpaRepository.findUnitJpaById(id);
        return unitMapper.toDomain(unitJpa);
    }
}
