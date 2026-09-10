package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.pantry_staples_line;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.AlimentJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.PantryStaplesJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.PantryStaplesLineJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UnitJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.pantry_staples.IPantryStaplesJpaRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.pantry_staples_line.PantryStaplesLineMapper;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IAlimentRepository;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IPantryStapleLineRepository;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IUnitRepository;
import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import io.github.oliviercap.chefduplacard.domain.pantry_staples.PantryStaples;
import io.github.oliviercap.chefduplacard.domain.pantry_staples.PantryStaplesLine;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class PantryStaplesLineRepository implements IPantryStapleLineRepository {

    private final IPantryStaplesLineJpaRepository jpaRepository;
    private final IUnitRepository unitRepository;
    private final IAlimentRepository alimentRepository;
    private final PantryStaplesLineMapper lineMapper;
    private final IPantryStaplesJpaRepository pantryStaplesJpaRepository;

    public PantryStaplesLineRepository(IPantryStaplesLineJpaRepository jpaRepository,
                                       IUnitRepository unitRepository, IAlimentRepository alimentRepository,
                                       PantryStaplesLineMapper lineMapper, IPantryStaplesJpaRepository pantryStaplesJpaRepository) {
        this.jpaRepository = jpaRepository;
        this.unitRepository = unitRepository;
        this.alimentRepository = alimentRepository;
        this.lineMapper = lineMapper;
        this.pantryStaplesJpaRepository = pantryStaplesJpaRepository;
    }


    @Transactional
    @Override
    public PantryStaplesLine modifyPantryStaplesLine(Long pantryStaplesLineId, BigDecimal quantity, Long unitId) {

        PantryStaplesLineJpa lineJpa = jpaRepository.findCompleteById(pantryStaplesLineId)
                .orElseThrow(() -> new DomainException("pantry staple line not found"));

        UnitJpa unitJpa= unitRepository.getReferenceById(unitId);

        lineJpa.setQuantity(quantity);
        lineJpa.setUnitJpa(unitJpa);

        return lineMapper.toDomain(lineJpa);
    }


    @Transactional
    @Override
    public void deletePantryStaplesLine(Long pantryStaplesLineId) {
        jpaRepository.deleteById(pantryStaplesLineId);
    }

    @Transactional
    @Override
    public PantryStaplesLine createPantryStaplesLine(Long pantryStaplesId, Long alimentId, BigDecimal quantity, Long unitId) {

        AlimentJpa alimentJpa = alimentRepository.findAlimentJpaById(alimentId)
                .orElseThrow(() -> new DomainException("aliment not found"));

        UnitJpa unitJpa = unitRepository.findUnitJpaById(unitId)
                .orElseThrow(() -> new DomainException("unit not found"));

        PantryStaplesJpa pantryStaplesJpa = pantryStaplesJpaRepository.getReferenceById(pantryStaplesId);

        PantryStaplesLineJpa lineJpa = new PantryStaplesLineJpa(
                pantryStaplesJpa,
                alimentJpa,
                unitJpa,
                quantity
        );

        PantryStaplesLineJpa saved = jpaRepository.save(lineJpa);

        return lineMapper.toDomain(saved);
    }
}
