package io.github.oliviercap.chefduplacard.adapters.persistence.mapper.aliment_type;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.AlimentTypeJpa;
import io.github.oliviercap.chefduplacard.domain.aliment_type.AlimentType;
import io.github.oliviercap.chefduplacard.domain.aliment_type.AlimentTypeId;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AlimentTypeMapper {

    public AlimentType toDomain(AlimentTypeJpa alimentTypeJpa) {
        Objects.requireNonNull(alimentTypeJpa, "aliment type jpa must not be null");

        return new AlimentType(
                alimentTypeJpa.getName(),
                new AlimentTypeId(alimentTypeJpa.getId())
        );
    }
}
