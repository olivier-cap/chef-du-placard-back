package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.unit;

import io.github.oliviercap.chefduplacard.adapters.persistence.converter.UnitJpaToDtoConverter;
import io.github.oliviercap.chefduplacard.adapters.persistence.dto.UnitDTO;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UnitJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.unit.IUnitMapper;
import io.github.oliviercap.chefduplacard.domain.unit.Unit;
import org.springframework.stereotype.Repository;

@Repository
public class UnitRepository implements IUnitRepository{
    private final IUnitJpaRepository unitJpaRepository;
    private final IUnitMapper unitMapper;
    private final UnitJpaToDtoConverter unitJpaToDtoConverter;

    public UnitRepository(IUnitJpaRepository unitJpaRepository, IUnitMapper unitMapper,
                          UnitJpaToDtoConverter unitJpaToDtoConverter) {
        this.unitJpaRepository = unitJpaRepository;
        this.unitMapper = unitMapper;
        this.unitJpaToDtoConverter = unitJpaToDtoConverter;
    }


    @Override
    public Unit findUnitByName(String name) {
        UnitJpa unitJpa = unitJpaRepository.findUnitJpaByName(name);
        UnitDTO unitDTO = unitJpaToDtoConverter.toDTO(unitJpa);
        return unitMapper.toDomain(unitDTO);
    }

    @Override
    public Unit findUnitById(Long id) {
        UnitJpa unitJpa = unitJpaRepository.findUnitJpaById(id);
        UnitDTO unitDTO = unitJpaToDtoConverter.toDTO(unitJpa);
        return unitMapper.toDomain(unitDTO);
    }
}
