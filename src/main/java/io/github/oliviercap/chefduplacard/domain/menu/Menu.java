package io.github.oliviercap.chefduplacard.domain.menu;

import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import io.github.oliviercap.chefduplacard.domain.user.User;

import java.util.List;
import java.util.Objects;

/**
 * Représente un menu, c.-à-d. Un groupe de recettes.
 * Contient un ensemble de lignes de menus. Une ligne de menus par recette présente.
 * Si la recette doit être réalisée plusieurs fois, plusieurs lignes de menus avec la même recette sont créées.
 */
public class Menu {

    private final MenuId id;
    private String name;
    private User user;
    private List<MenuLine> menuLines;

    public Menu(MenuId id, String name, User user, List<MenuLine> menuLines) {
        if(id == null) {
            throw new DomainException("id must not be null");
        }

        if(user == null) {
            throw new DomainException("user must not be null");
        }
        this.id = id;
        this.user = user;
        this.name = name;
        this.menuLines = menuLines;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MenuLine> getMenuLines() {
        return List.copyOf(menuLines);
    }

    public MenuId getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Menu menu)) return false;
        return Objects.equals(id, menu.id) && Objects.equals(name, menu.name) && Objects.equals(user, menu.user) && Objects.equals(menuLines, menu.menuLines);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, user, menuLines);
    }
}
