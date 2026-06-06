package io.github.oliviercap.chefduplacard.configuration.root;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.menu.MenuRepository;
import io.github.oliviercap.chefduplacard.application.getmenu.GetMenuUseCase;
import io.github.oliviercap.chefduplacard.application.getmenu.ports.IGetMenuOutputPort;
import io.github.oliviercap.chefduplacard.application.ports.query.IMenuViewQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GetMenuRoot {

    @Bean
    public GetMenuUseCase GetMenuRoot(
            IMenuViewQuery menuViewQuery,
            IGetMenuOutputPort outputPort
    ) {
        return new GetMenuUseCase(
                menuViewQuery,
                outputPort
        );
    }
}
