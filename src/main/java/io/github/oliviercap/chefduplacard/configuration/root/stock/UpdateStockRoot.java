package io.github.oliviercap.chefduplacard.configuration.root.stock;

import io.github.oliviercap.chefduplacard.application.ports.persistence.IRecipeRepository;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IStockRepository;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IUserRepository;
import io.github.oliviercap.chefduplacard.application.stock.updatestock.UpdateStockUseCase;
import io.github.oliviercap.chefduplacard.application.stock.updatestock.port.IUpdateStockOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UpdateStockRoot {

    @Bean
    public UpdateStockUseCase UpdateStockUseCase(
        IStockRepository stockRepository,
        IRecipeRepository recipeRepository,
        IUpdateStockOutputPort outputPort,
        IUserRepository userRepository
    ) {
        return new UpdateStockUseCase(
                recipeRepository,
                stockRepository,
                outputPort,
                userRepository
        );
    }
}
