package io.github.oliviercap.chefduplacard.configuration.root.pantry_staples;

import io.github.oliviercap.chefduplacard.application.pantry_staples.delete_pantry_staples_line.DeletePantryStaplesLineUseCase;
import io.github.oliviercap.chefduplacard.application.pantry_staples.delete_pantry_staples_line.ports.IDeletePantryStaplesLineOutputPort;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IPantryStapleLineRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeletePantryStaplesLineRoot {

    @Bean
    DeletePantryStaplesLineUseCase DeletePantryStaplesLineRoot(
          IPantryStapleLineRepository repository,
          IDeletePantryStaplesLineOutputPort outputPort
    ) {
        return new DeletePantryStaplesLineUseCase(
                repository,
                outputPort
        );
    }
}
