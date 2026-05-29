package io.github.oliviercap.chefduplacard.configuration.root;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.aliment.AlimentRepository;
import io.github.oliviercap.chefduplacard.application.modifyaliment.ModifyAlimentUseCase;
import io.github.oliviercap.chefduplacard.application.modifyaliment.ports.IModifyAlimentOutputPort;
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
