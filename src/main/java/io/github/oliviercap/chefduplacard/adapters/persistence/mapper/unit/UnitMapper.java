package io.github.oliviercap.chefduplacard.adapters.persistence.mapper.unit;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.UnitJpa;
import io.github.oliviercap.chefduplacard.domain.unit.Unit;
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
          unitJpa.getName(),
          unitJpa.getSymbol()
        );
    }

}
