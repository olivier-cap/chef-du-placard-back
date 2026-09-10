package io.github.oliviercap.chefduplacard.configuration.root.pantry_staples;

import io.github.oliviercap.chefduplacard.application.pantry_staples.create_pantry_staples_line.CreatePantryStaplesLineUseCase;
import io.github.oliviercap.chefduplacard.application.pantry_staples.create_pantry_staples_line.ports.ICreatePantryStaplesLineOutputPort;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IPantryStapleLineRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreatePantryStaplesLineRoot {

    @Bean
    CreatePantryStaplesLineUseCase CreatePantryStaplesLineRoot(
            IPantryStapleLineRepository repository,
            ICreatePantryStaplesLineOutputPort outputPort
    ) {
        return new CreatePantryStaplesLineUseCase(
                repository,
                outputPort
        );
    }
}
