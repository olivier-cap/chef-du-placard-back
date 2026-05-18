package io.github.oliviercap.chefduplacard.application.roots;

import io.github.oliviercap.chefduplacard.application.ports.persistence.IRecipeRepository;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IStockRepository;
import io.github.oliviercap.chefduplacard.application.cookablemenus.ICookableMenusOutputPort;
import io.github.oliviercap.chefduplacard.application.cookablemenus.CookableMenusUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CookableMenusRoot {

    /**
     * Classe responsable de l'inversion de dépendance.
     * Injection des classes externes dans le usecase cookableMenus
     * @param recipeRepository
     * @param stockRepository
     * @return
     */
    @Bean
    public CookableMenusUseCase CookableMenusUseCase(
            IRecipeRepository recipeRepository,
            IStockRepository stockRepository,
            ICookableMenusOutputPort cookableMenusOutputPort
    ) {
        return new CookableMenusUseCase(
                recipeRepository,
                stockRepository,
                cookableMenusOutputPort
        );
    }
}
