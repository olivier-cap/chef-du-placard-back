package io.github.oliviercap.chefduplacard.application.converter.alimentresponse;

import io.github.oliviercap.chefduplacard.application.dto.AlimentResponse;
import io.github.oliviercap.chefduplacard.domain.food.Aliment;

public interface IAlimentToAlimentResponse {
    public AlimentResponse toDTO(Aliment aliment);
}
