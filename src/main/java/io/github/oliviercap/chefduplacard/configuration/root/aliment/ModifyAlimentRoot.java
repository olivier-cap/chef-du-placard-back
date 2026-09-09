package io.github.oliviercap.chefduplacard.configuration.root.aliment;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.aliment.AlimentRepository;
import io.github.oliviercap.chefduplacard.application.aliment.modifyaliment.ModifyAlimentUseCase;
import io.github.oliviercap.chefduplacard.application.aliment.modifyaliment.ports.IModifyAlimentOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModifyAlimentRoot {

    @Bean
    public ModifyAlimentUseCase ModifyAlimentRoot(
            IModifyAlimentOutputPort outputPort,
            AlimentRepository alimentRepository) {
        return new ModifyAlimentUseCase(
                alimentRepository,
                outputPort
        );
    }
}
