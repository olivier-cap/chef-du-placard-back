package io.github.oliviercap.chefduplacard.application.ports.persistence;

import io.github.oliviercap.chefduplacard.domain.menu.Menu;

import java.util.Optional;

public interface IMenuRepository {
    Optional<Menu> findByName(String menuName);
    void save(Menu menu);
}
