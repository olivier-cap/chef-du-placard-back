package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.pantry_staples_line;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.PantryStaplesLineJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UnitJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.pantry_staples_line.PantryStaplesLineMapper;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IPantryStapleLineRepository;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IUnitRepository;
import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import io.github.oliviercap.chefduplacard.domain.pantry_staples.PantryStaplesLine;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class PantryStaplesLineRepository implements IPantryStapleLineRepository {

    private final IPantryStaplesLineJpaRepository jpaRepository;
    private final IUnitRepository unitRepository;
    private final PantryStaplesLineMapper lineMapper;

    public PantryStaplesLineRepository(IPantryStaplesLineJpaRepository jpaRepository,
                                       IUnitRepository unitRepository,
                                       PantryStaplesLineMapper lineMapper) {
        this.jpaRepository = jpaRepository;
        this.unitRepository = unitRepository;
        this.lineMapper = lineMapper;
    }


    @Override
    public PantryStaplesLine modifyPantryStaplesLine(Long pantryStaplesLineId, BigDecimal quantity, Long unitId) {

        PantryStaplesLineJpa lineJpa = jpaRepository.findCompleteById(pantryStaplesLineId)
                .orElseThrow(() -> new DomainException("pantry staple line not found"));

        UnitJpa unitJpa= unitRepository.getReferenceById(unitId);

        lineJpa.setQuantity(quantity);
        lineJpa.setUnitJpa(unitJpa);

        return lineMapper.toDomain(lineJpa);
    }
}
