package io.github.oliviercap.chefduplacard.configuration.root;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.menu.MenuRepository;
import io.github.oliviercap.chefduplacard.application.getmenu.GetMenuUseCase;
import io.github.oliviercap.chefduplacard.application.getmenu.ports.IGetMenuOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GetMenuRoot {

    @Bean
    public GetMenuUseCase GetMenuRoot(
            MenuRepository menuRepository,
            IGetMenuOutputPort outputPort
    ) {
        return new GetMenuUseCase(
                menuRepository,
                outputPort
        );
    }
}
