package io.github.oliviercap.chefduplacard.adapters.persistence.mapper.aliment;

import io.github.oliviercap.chefduplacard.adapters.persistence.dto.AlimentDTO;
import io.github.oliviercap.chefduplacard.domain.food.Aliment;

public interface IAlimentMapper {
    Aliment toDomain(AlimentDTO alimentDTO);
}
