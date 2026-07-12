package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.unit;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UnitJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.unit.UnitMapper;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IUnitRepository;
import io.github.oliviercap.chefduplacard.domain.unit.Unit;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
    public Optional<Unit> findUnitByName(String name) {
        return unitJpaRepository.findUnitJpaByName(name)
                .map(unitMapper::toDomain);
    }

    @Override
    public Optional<Unit> findUnitById(Long id) {
        return unitJpaRepository.findUnitJpaById(id).
                map(unitMapper::toDomain);
    }

    @Override
    public Optional<UnitJpa> findUnitJpaById(Long id) {
        return unitJpaRepository.findById(id);
    }

    @Override
    public List<UnitJpa> findAllJpa() {
        return unitJpaRepository.findAll();
    }

    @Override
    public Optional<UnitJpa> findJpaByName(String name) {
        return unitJpaRepository.findUnitJpaByName(name);
    }

    @Override
    public UnitJpa getReferenceById(Long id) {
        return this.getReferenceById(id);
    }
}
