package io.github.oliviercap.chefduplacard.configuration.root.menu;

import io.github.oliviercap.chefduplacard.application.menu.get_all_menus.GetAllMenusUseCase;
import io.github.oliviercap.chefduplacard.application.menu.get_all_menus.ports.IGetAllMenusOutputPort;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IMenuRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GetAllMenusRoot {

    @Bean
    GetAllMenusUseCase GetAllMenusRoot(
            IMenuRepository repository,
            IGetAllMenusOutputPort outputPort
    ) {
        return new GetAllMenusUseCase(
                repository,
                outputPort
        );
    }
}
