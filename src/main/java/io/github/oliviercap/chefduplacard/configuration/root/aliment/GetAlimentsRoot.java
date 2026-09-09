package io.github.oliviercap.chefduplacard.configuration.root.aliment;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.aliment.AlimentRepository;
import io.github.oliviercap.chefduplacard.application.aliment.getaliments.GetAlimentsUseCase;
import io.github.oliviercap.chefduplacard.application.aliment.getaliments.ports.IGetAlimentsOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GetAlimentsRoot {

    @Bean
    public GetAlimentsUseCase GetAlimentsRoot(
            AlimentRepository alimentRepository,
            IGetAlimentsOutputPort iGetAlimentsOutputPort) {
        return new GetAlimentsUseCase(
                alimentRepository,
                iGetAlimentsOutputPort
        );
    }
}
