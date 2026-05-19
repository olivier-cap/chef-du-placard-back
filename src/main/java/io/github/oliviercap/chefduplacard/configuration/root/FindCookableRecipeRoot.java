package io.github.oliviercap.chefduplacard.configuration.root;

import io.github.oliviercap.chefduplacard.application.ports.persistence.IRecipeRepository;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IStockRepository;
import io.github.oliviercap.chefduplacard.application.cookablerecipes.ports.IFindCookableRecipesOutputPort;
import io.github.oliviercap.chefduplacard.application.cookablerecipes.FindCookableRecipesUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Classe dont le rôle est d'injecter les classes de la couche application (et autres si besoins) vers les couches métiers.
 * -> injection de dépendance.
 * Classe volontairement laissée à spring :
 *  - repository sont des beans, donc obligatoirement gérés par spring
 *  - changement de plateforme (ex android) va nécessiter changement de root / de dispositif d'injection de toute façon
 */
@Configuration
public class FindCookableRecipeRoot {

    /**
     * Classe responsable de l'inversion de dépendance.
     * Injection des classes externes dans le usecase findCookableRecipes
     * @param recipeRepository
     * @param stockRepository
     * @param outputPort
     * @return
     */
    @Bean
    public FindCookableRecipesUseCase findCookableRecipesUseCase(
            IRecipeRepository recipeRepository,
            IStockRepository stockRepository,
            IFindCookableRecipesOutputPort outputPort
    ) {
        return new FindCookableRecipesUseCase(
                recipeRepository,
                stockRepository,
                outputPort
        );
    }

}
