package io.github.oliviercap.chefduplacard.domain.menu;

import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;

import java.util.List;

/**
 * Représente un menu, c.a.d un groupe de recettes.
 * Contient un ensemble de lignes de menus. Une ligne de menus par recette présente.
 * Si la recette doit être réalisée plusieurs fois, plusieurs lignes de menus avec la même recette sont créées.
 */
public class Menu {

    private String name;
    private List<MenuLine> menuLines;

    public Menu(String name, List<MenuLine> menuLines) {
        if(name.isBlank()) {
            throw new DomainException("menu name must not be blank");
        }

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


}
