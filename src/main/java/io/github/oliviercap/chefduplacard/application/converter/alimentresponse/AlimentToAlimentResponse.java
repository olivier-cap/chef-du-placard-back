package io.github.oliviercap.chefduplacard.application.converter.alimentresponse;

import io.github.oliviercap.chefduplacard.application.dto.AlimentResponse;
import io.github.oliviercap.chefduplacard.domain.food.Aliment;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AlimentToAlimentResponse implements IAlimentToAlimentResponse {
    @Override
    public AlimentResponse toDTO(Aliment aliment) {
        Objects.requireNonNull(aliment, "aliment must not be null");

        return new AlimentResponse(aliment.getName(),
                aliment.getDescription(),
                aliment.isActive());
    }
}
