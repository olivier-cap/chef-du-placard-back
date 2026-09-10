package io.github.oliviercap.chefduplacard.adapters.persistence.mapper.aliment;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.AlimentJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.aliment_type.AlimentTypeMapper;
import io.github.oliviercap.chefduplacard.domain.food.Aliment;
import io.github.oliviercap.chefduplacard.domain.food.AlimentId;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;


/*
Classe responsable de transposer des Aliment (du Domaine) en Entités Jpa et inversement.
 */
@Component
public class AlimentMapper {

    private final AlimentTypeMapper alimentTypeMapper;

    public AlimentMapper(AlimentTypeMapper alimentTypeMapper) {
        this.alimentTypeMapper = alimentTypeMapper;
    }

    public Aliment toDomain(AlimentJpa alimentJpa) {
        Objects.requireNonNull(alimentJpa,"alimentJpa must not be null");

        return new Aliment(
                new AlimentId(alimentJpa.getId()),
                alimentJpa.getName(),
                alimentJpa.getDescription(),
                alimentJpa.isActive(),
                alimentJpa.getAlimentTypes().stream()
                        .map(alimentTypeMapper::toDomain)
                        .collect(Collectors.toSet())
        );
    }

}
