package io.github.oliviercap.chefduplacard.adapters.persistence.mapper.menu;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.MenuJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.MenuLineJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.menuline.MenuLineMapper;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.user.UserMapper;
import io.github.oliviercap.chefduplacard.domain.menu.Menu;
import io.github.oliviercap.chefduplacard.domain.menu.MenuId;
import org.springframework.stereotype.Component;

@Component
public class MenuMapper {

    private final MenuLineMapper menuLineMapper;
    private final UserMapper userMapper;

    public MenuMapper(MenuLineMapper menuLineMapper, UserMapper userMapper) {
        this.menuLineMapper = menuLineMapper;
        this.userMapper = userMapper;
    }

    public Menu toDomain(MenuJpa menuJpa) {
        return new Menu(
                new MenuId(menuJpa.getId()),
                menuJpa.getName(),
                userMapper.toDomain(menuJpa.getUserJpa()),
                menuJpa.getMenuLineJpaList().stream()
                        .map(menuLineMapper::toDomain)
                        .toList()
        );
    }
}
