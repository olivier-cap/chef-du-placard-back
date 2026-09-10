package io.github.oliviercap.chefduplacard.configuration.root.unit;

import io.github.oliviercap.chefduplacard.application.ports.persistence.IUnitRepository;
import io.github.oliviercap.chefduplacard.application.unit.get_all_units.GetAllUnitsUseCase;
import io.github.oliviercap.chefduplacard.application.unit.get_all_units.ports.IGetAllUnitsOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GetAllUnitsRoot {

    @Bean
    GetAllUnitsUseCase GetAllUnitsRoot(
            IUnitRepository repository,
            IGetAllUnitsOutputPort outputPort
    ) {
        return new GetAllUnitsUseCase(
                repository,
                outputPort
        );
    }
}
