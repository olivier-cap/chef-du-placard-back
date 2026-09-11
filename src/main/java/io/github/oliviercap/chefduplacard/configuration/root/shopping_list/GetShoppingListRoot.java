package io.github.oliviercap.chefduplacard.configuration.root.shopping_list;

import io.github.oliviercap.chefduplacard.application.ports.persistence.IShoppingListRepository;
import io.github.oliviercap.chefduplacard.application.shopping_list.get_shopping_list.GetShoppingListUseCase;
import io.github.oliviercap.chefduplacard.application.shopping_list.get_shopping_list.ports.IGetShoppingListOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GetShoppingListRoot {

    @Bean
    GetShoppingListUseCase GetShoppingListRoot(
            IShoppingListRepository repository,
            IGetShoppingListOutputPort outputPort
    ) {
        return new GetShoppingListUseCase(
                repository,
                outputPort
        );
    }
}
