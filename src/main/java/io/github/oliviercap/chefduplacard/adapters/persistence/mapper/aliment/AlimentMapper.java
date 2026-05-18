package io.github.oliviercap.chefduplacard.adapters.persistence.mapper.aliment;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.AlimentJpa;
import io.github.oliviercap.chefduplacard.domain.food.Aliment;
import org.springframework.stereotype.Component;

import java.util.Objects;


/*
Classe responsable de transposer des Aliment (du Domaine) en Entités Jpa et inversement.
 */
@Component
public class AlimentMapper {

    public Aliment toDomain(AlimentJpa alimentJpa) {
        Objects.requireNonNull(alimentJpa,"alimentJpa must not be null");

        return new Aliment(
                alimentJpa.getName(),
                alimentJpa.getDescription(),
                alimentJpa.isActive()
        );
    }

}
