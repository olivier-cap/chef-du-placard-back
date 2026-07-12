package io.github.oliviercap.chefduplacard.configuration.root;

import io.github.oliviercap.chefduplacard.application.createnewrecipe.CreateNewRecipeUseCase;
import io.github.oliviercap.chefduplacard.application.createnewrecipe.ports.ICreateNewRecipeOutputPort;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IRecipeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreateNewRecipeRoot {

    @Bean
    public CreateNewRecipeUseCase createNewRecipeUseCase(
         IRecipeRepository recipeRepository,
         ICreateNewRecipeOutputPort outputPort
    ){
        return new CreateNewRecipeUseCase(
                recipeRepository,
                outputPort
        );
    }
}
