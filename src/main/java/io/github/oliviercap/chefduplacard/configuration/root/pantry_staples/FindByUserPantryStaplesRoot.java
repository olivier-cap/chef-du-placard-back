package io.github.oliviercap.chefduplacard.configuration.root.pantry_staples;

import io.github.oliviercap.chefduplacard.application.pantry_staples.findByUser.FindByUserPantryStaplesUseCase;
import io.github.oliviercap.chefduplacard.application.pantry_staples.findByUser.ports.IFindByUserPantryStaplesOutputPort;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IPantryStaplesRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FindByUserPantryStaplesRoot {

    @Bean
    public FindByUserPantryStaplesUseCase FindByUserPantryStaplesRoot(
          IPantryStaplesRepository repository,
          IFindByUserPantryStaplesOutputPort outputPort
    ) {
        return new FindByUserPantryStaplesUseCase(
                repository,
                outputPort
        );
    }
}
