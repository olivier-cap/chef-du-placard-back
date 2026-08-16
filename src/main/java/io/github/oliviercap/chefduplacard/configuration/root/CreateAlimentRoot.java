package io.github.oliviercap.chefduplacard.configuration.root;

import io.github.oliviercap.chefduplacard.application.createaliment.CreateAlimentUseCase;
import io.github.oliviercap.chefduplacard.application.createaliment.ports.ICreateAlimentOutputPort;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IAlimentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreateAlimentRoot {

    @Bean
    public CreateAlimentUseCase createAlimentUseCase(
            ICreateAlimentOutputPort outputPort,
            IAlimentRepository alimentRepository
    ) {
        return new CreateAlimentUseCase(
                outputPort,
                alimentRepository
        );
    }
}
