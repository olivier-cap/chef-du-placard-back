package io.github.oliviercap.chefduplacard.application.ports.persistence;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.menu.SaveNewMenuDTO;
import io.github.oliviercap.chefduplacard.domain.menu.Menu;

import java.util.Optional;

public interface IMenuRepository {
    Optional<Menu> findById(Long menuId);
    void save(SaveNewMenuDTO menuDTO);
}
