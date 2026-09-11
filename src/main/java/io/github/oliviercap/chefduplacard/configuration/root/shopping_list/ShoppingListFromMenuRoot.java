package io.github.oliviercap.chefduplacard.configuration.root.shopping_list;

import io.github.oliviercap.chefduplacard.application.ports.persistence.IMenuRepository;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IPantryStaplesRepository;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IShoppingListRepository;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IStockRepository;
import io.github.oliviercap.chefduplacard.application.shopping_list.shopping_list_from_menu.ShoppingListFromMenuUseCase;
import io.github.oliviercap.chefduplacard.application.shopping_list.shopping_list_from_menu.ports.IShoppingListFromMenuOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShoppingListFromMenuRoot {

    @Bean
    ShoppingListFromMenuUseCase ShoppingListFromMenuRoot(
            IShoppingListRepository repository,
            IShoppingListFromMenuOutputPort outputPort,
            IMenuRepository menuRepository,
            IStockRepository stockRepository,
            IPantryStaplesRepository pantryStaplesRepository
    ) {
        return new ShoppingListFromMenuUseCase(
                repository, outputPort, menuRepository, stockRepository, pantryStaplesRepository);
    }
}
