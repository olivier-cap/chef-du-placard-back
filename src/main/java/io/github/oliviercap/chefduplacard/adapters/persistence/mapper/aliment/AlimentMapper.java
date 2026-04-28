package io.github.oliviercap.chefduplacard.adapters.persistence.mapper.aliment;

import io.github.oliviercap.chefduplacard.adapters.persistence.dto.AlimentDTO;
import io.github.oliviercap.chefduplacard.domain.food.Aliment;
import org.springframework.stereotype.Component;

@Component
public class AlimentMapper implements IAlimentMapper{
    public AlimentMapper() {
    }

    @Override
    public Aliment toDomain(AlimentDTO alimentDTO) {
        return new Aliment(alimentDTO.name(),
                alimentDTO.description(),
                alimentDTO.active());
    }
}
