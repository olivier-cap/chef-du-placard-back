package io.github.oliviercap.chefduplacard.configuration.root;

import io.github.oliviercap.chefduplacard.application.ports.persistence.IMenuRepository;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IRecipeRepository;
import io.github.oliviercap.chefduplacard.application.savenewmenu.SaveNewMenuUseCase;
import io.github.oliviercap.chefduplacard.application.savenewmenu.port.ISaveNewMenuOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SaveNewMenuRoot {

    @Bean
    public SaveNewMenuUseCase SaveNewMenuRoot(
        IMenuRepository menuRepository,
        IRecipeRepository recipeRepository,
        ISaveNewMenuOutputPort outputPort
    ) {
        return new SaveNewMenuUseCase(
                menuRepository,
                recipeRepository,
                outputPort
        );
    }
}
