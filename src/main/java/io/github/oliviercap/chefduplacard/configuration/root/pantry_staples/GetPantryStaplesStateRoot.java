package io.github.oliviercap.chefduplacard.configuration.root.pantry_staples;

import io.github.oliviercap.chefduplacard.application.pantry_staples.get_pantry_staples_state.GetPantryStaplesStateUseCase;
import io.github.oliviercap.chefduplacard.application.pantry_staples.get_pantry_staples_state.ports.IGetPantryStaplesStateOutputPort;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IPantryStaplesRepository;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IStockRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GetPantryStaplesStateRoot {

    @Bean
    public GetPantryStaplesStateUseCase GetPantryStaplesStateRoot (
            IStockRepository repository,
            IPantryStaplesRepository pantryStaplesRepository,
            IGetPantryStaplesStateOutputPort outputPort
    ) {
        return new GetPantryStaplesStateUseCase(
                repository,
                pantryStaplesRepository,
                outputPort
        );
    }
}
