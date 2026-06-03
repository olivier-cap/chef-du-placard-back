package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.menu;

import io.github.oliviercap.chefduplacard.adapters.persistence.mapper.menu.MenuMapper;
import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import io.github.oliviercap.chefduplacard.domain.menu.Menu;

import java.util.Optional;

public class MenuRepository {

    private final IMenuJpaRepository menuJpaRepository;
    private final MenuMapper menuMapper;

    public MenuRepository(IMenuJpaRepository menuJpaRepository,
                          MenuMapper menuMapper) {
        this.menuJpaRepository = menuJpaRepository;
        this.menuMapper = menuMapper;
    }

    public Optional<Menu> findByName(String menuName) {
        return menuJpaRepository.findMenuDetailsByName(menuName)
                .map(menuMapper::toDomain);
    }

}
