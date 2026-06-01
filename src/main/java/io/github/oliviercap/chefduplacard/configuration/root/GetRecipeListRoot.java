package io.github.oliviercap.chefduplacard.configuration.root;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.recipe.RecipeRepository;
import io.github.oliviercap.chefduplacard.application.getrecipelist.GetRecipeListUseCase;
import io.github.oliviercap.chefduplacard.application.getrecipelist.ports.IGetRecipeListOutPort;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IRecipeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GetRecipeListRoot {

    @Bean
    GetRecipeListUseCase GetRecipeListRoot(
        IRecipeRepository recipeRepository,
        IGetRecipeListOutPort outputPort
    ) {
        return new GetRecipeListUseCase(
                recipeRepository,
                outputPort
        );
    }
}
