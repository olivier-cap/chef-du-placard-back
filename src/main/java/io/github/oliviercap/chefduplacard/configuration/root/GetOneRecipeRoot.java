package io.github.oliviercap.chefduplacard.configuration.root;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.recipe.RecipeRepository;
import io.github.oliviercap.chefduplacard.application.getonerecipe.GetOneRecipeUseCase;
import io.github.oliviercap.chefduplacard.application.getonerecipe.ports.IGetOneRecipeOutputPort;
import io.github.oliviercap.chefduplacard.application.getrecipelist.GetRecipeListUseCase;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IRecipeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GetOneRecipeRoot {

    @Bean
    public GetOneRecipeUseCase GetOneRecipeRoot(
            IRecipeRepository recipeRepository,
            IGetOneRecipeOutputPort outputPort
    ) {
        return new GetOneRecipeUseCase(
                recipeRepository,
                outputPort
        );
    }
}
