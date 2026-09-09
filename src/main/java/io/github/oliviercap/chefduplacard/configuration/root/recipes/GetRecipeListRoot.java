package io.github.oliviercap.chefduplacard.configuration.root.recipes;

import io.github.oliviercap.chefduplacard.application.recipes.getrecipelist.GetRecipeListUseCase;
import io.github.oliviercap.chefduplacard.application.recipes.getrecipelist.ports.IGetRecipeListOutPort;
import io.github.oliviercap.chefduplacard.application.ports.query.IGetRecipeListViewQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GetRecipeListRoot {

    @Bean
    GetRecipeListUseCase GetRecipeListRoot(
        IGetRecipeListViewQuery getRecipeListViewQuery,
        IGetRecipeListOutPort outputPort
    ) {
        return new GetRecipeListUseCase(
                getRecipeListViewQuery,
                outputPort
        );
    }
}
