package io.github.oliviercap.chefduplacard.configuration.root.menu;

import io.github.oliviercap.chefduplacard.application.menu.getmenu.GetMenuUseCase;
import io.github.oliviercap.chefduplacard.application.menu.getmenu.ports.IGetMenuOutputPort;
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
