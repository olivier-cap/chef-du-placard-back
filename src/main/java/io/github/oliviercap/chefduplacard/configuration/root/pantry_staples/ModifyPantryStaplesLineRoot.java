package io.github.oliviercap.chefduplacard.configuration.root.pantry_staples;

import io.github.oliviercap.chefduplacard.application.pantry_staples.modify_pantry_staples_line.ModifyPantryStaplesLineUseCase;
import io.github.oliviercap.chefduplacard.application.pantry_staples.modify_pantry_staples_line.ports.IModifyPantryStaplesLineOutputPort;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IPantryStapleLineRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModifyPantryStaplesLineRoot {

    @Bean
    ModifyPantryStaplesLineUseCase ModifyPantryStaplesLineRoot(
            IPantryStapleLineRepository lineRepository,
            IModifyPantryStaplesLineOutputPort outputPort
    ) {
        return new ModifyPantryStaplesLineUseCase(
                lineRepository,
                outputPort
        );
    }
}
