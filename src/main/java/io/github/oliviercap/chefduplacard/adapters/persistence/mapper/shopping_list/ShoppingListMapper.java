package io.github.oliviercap.chefduplacard.adapters.persistence.mapper.shopping_list;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.ShoppingListJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.shopping_list_line.ShoppingListLineMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.user.UserMapper;
import io.github.oliviercap.chefduplacard.domain.shopping_list.ShoppingList;
import io.github.oliviercap.chefduplacard.domain.shopping_list.ShoppingListId;
import org.springframework.stereotype.Component;

@Component
public class ShoppingListMapper {

    private final UserMapper userMapper;
    private final ShoppingListLineMapper shoppingListLineMapper;

    public ShoppingListMapper(UserMapper userMapper, ShoppingListLineMapper shoppingListLineMapper) {
        this.userMapper = userMapper;
        this.shoppingListLineMapper = shoppingListLineMapper;
    }

    public ShoppingList toDomain(ShoppingListJpa shoppingListJpa) {
        return new ShoppingList(
                new ShoppingListId(shoppingListJpa.getId()),
                userMapper.toDomain(shoppingListJpa.getUserJpa()),
                shoppingListJpa.getDate(),
                shoppingListJpa.getShoppingListLineJpaList().stream()
                        .map(shoppingListLineMapper::toDomain)
                        .toList()
        );
    }
}
