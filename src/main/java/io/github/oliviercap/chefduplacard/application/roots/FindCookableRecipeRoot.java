package io.github.oliviercap.chefduplacard.application.roots;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.recipe.IRecipeRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.stock.IStockRepository;
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

    @Bean
    public FindCookableRecipesUseCase findCookableRecipesUseCase(
            IRecipeRepository recipeRepository,
            IStockRepository stockRepository
    ) {
        return new FindCookableRecipesUseCase(
                recipeRepository,
                stockRepository
        );
    }

}
