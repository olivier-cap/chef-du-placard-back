package io.github.oliviercap.chefduplacard.adapters.persistence.mapper.unit;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UnitJpa;
import io.github.oliviercap.chefduplacard.domain.unit.Unit;
import io.github.oliviercap.chefduplacard.domain.unit.UnitId;
import org.springframework.stereotype.Component;

import java.util.Objects;

/*
Classe responsable de transposer des Aliment (du Domaine) en Entités Jpa et inversement.
 */
@Component
public class UnitMapper{

    public Unit toDomain(UnitJpa unitJpa){
        Objects.requireNonNull(unitJpa, "unitjpa must not be null");

        return new Unit(
                new UnitId(unitJpa.getId()),
                unitJpa.getName(),
                unitJpa.getSymbol()
        );
    }

    public UnitJpa toEntity(Unit unit) {
        Objects.requireNonNull(unit, "unit must not be null");

        return new UnitJpa(
                unit.getName(),
                unit.getSymbol()
        );
    }

}
