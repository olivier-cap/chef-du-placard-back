package io.github.oliviercap.chefduplacard.adapters.persistence.mapper.aliment;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.AlimentJpa;
import io.github.oliviercap.chefduplacard.domain.food.Aliment;
import io.github.oliviercap.chefduplacard.domain.food.AlimentId;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AlimentMapperWithoutType {

    public Aliment toDomain(AlimentJpa alimentJpa) {
        Objects.requireNonNull(alimentJpa,"alimentJpa must not be null");

        return new Aliment(
                new AlimentId(alimentJpa.getId()),
                alimentJpa.getName(),
                alimentJpa.getDescription(),
                alimentJpa.isActive()
        );
    }
}
