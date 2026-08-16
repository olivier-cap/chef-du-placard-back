package io.github.oliviercap.chefduplacard.adapters.persistence.mapper.menu;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.MenuJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.MenuLineJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.menuline.MenuLineMapper;
import io.github.oliviercap.chefduplacard.domain.menu.Menu;
import io.github.oliviercap.chefduplacard.domain.menu.MenuId;
import org.springframework.stereotype.Component;

@Component
public class MenuMapper {

    private final MenuLineMapper menuLineMapper;

    public MenuMapper(MenuLineMapper menuLineMapper) {
        this.menuLineMapper = menuLineMapper;
    }

    public Menu toDomain(MenuJpa menuJpa) {
        return new Menu(
                new MenuId(menuJpa.getId()),
                menuJpa.getName(),
                menuJpa.getMenuLineJpaList().stream()
                        .map(menuLineMapper::toDomain)
                        .toList()
        );
    }
}
