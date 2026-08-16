package io.github.oliviercap.chefduplacard.domain.menu;

import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;

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
    private List<MenuLine> menuLines;

    public Menu(MenuId id, String name, List<MenuLine> menuLines) {
        if(id == null) {
            throw new DomainException("id must not be null");
        }

        if(name.isBlank()) {
            throw new DomainException("menu name must not be blank");
        }

        this.id = id;
        this.name = name;
        this.menuLines = menuLines;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name.isBlank()) {
            throw new DomainException("menu name must not be blank");
        }
        this.name = name;
    }

    public List<MenuLine> getMenuLines() {
        return menuLines;
    }

    public void setMenuLines(List<MenuLine> menuLines) {
        this.menuLines = menuLines;
    }

    public MenuId getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Menu menu)) return false;
        return Objects.equals(id, menu.id) && Objects.equals(name, menu.name) && Objects.equals(menuLines, menu.menuLines);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, menuLines);
    }
}
