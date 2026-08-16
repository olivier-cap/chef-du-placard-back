package io.github.oliviercap.chefduplacard.domain.menu;

import io.github.oliviercap.chefduplacard.domain.recipe.Recipe;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Représente une ligne d'un menu.
 */
public class MenuLine {

    private final MenuLineId id;
    private Recipe recipe;
    private BigDecimal nbPerson;

    public MenuLine(MenuLineId id, Recipe recipe, BigDecimal nbPerson) {
        this.id = id;
        this.recipe = recipe;
        this.nbPerson = nbPerson;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public BigDecimal getNbPerson() {
        return nbPerson;
    }

    public void setNbPerson(BigDecimal nbPerson) {
        this.nbPerson = nbPerson;
    }

    public MenuLineId getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MenuLine menuLine)) return false;
        return Objects.equals(id, menuLine.id) && Objects.equals(recipe, menuLine.recipe) && Objects.equals(nbPerson, menuLine.nbPerson);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, recipe, nbPerson);
    }
}
