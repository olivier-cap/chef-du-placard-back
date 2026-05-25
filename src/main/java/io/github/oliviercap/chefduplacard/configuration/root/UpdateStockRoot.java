package io.github.oliviercap.chefduplacard.configuration.root;

import io.github.oliviercap.chefduplacard.application.ports.persistence.IRecipeRepository;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IStockRepository;
import io.github.oliviercap.chefduplacard.application.updatestock.UpdateStockUseCase;
import io.github.oliviercap.chefduplacard.application.updatestock.port.IUpdateStockOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UpdateStockRoot {

    @Bean
    public UpdateStockUseCase UpdateStockUseCase(
        IStockRepository stockRepository,
        IRecipeRepository recipeRepository,
        IUpdateStockOutputPort outputPort
    ) {
        return new UpdateStockUseCase(
                recipeRepository,
                stockRepository,
                outputPort
        );
    }
}
