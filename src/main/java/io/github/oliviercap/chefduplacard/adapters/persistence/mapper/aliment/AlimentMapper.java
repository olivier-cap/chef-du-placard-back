package io.github.oliviercap.chefduplacard.adapters.persistence.mapper.aliment;

import io.github.oliviercap.chefduplacard.adapters.persistence.dto.AlimentDTO;
import io.github.oliviercap.chefduplacard.domain.food.Aliment;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AlimentMapper implements IAlimentMapper{
    public AlimentMapper() {
    }

    @Override
    public Aliment toDomain(AlimentDTO alimentDTO) {
        Objects.requireNonNull(alimentDTO, "alimentDTO must not be null");

        return new Aliment(alimentDTO.name(), alimentDTO.description(),
                alimentDTO.active());
    }
}
