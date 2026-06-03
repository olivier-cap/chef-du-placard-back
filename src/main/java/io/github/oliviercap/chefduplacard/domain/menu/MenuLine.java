package io.github.oliviercap.chefduplacard.domain.menu;

import io.github.oliviercap.chefduplacard.domain.recipe.Recipe;

import java.math.BigDecimal;

/**
 * Représente une ligne d'un menu.
 */
public class MenuLine {

    private Recipe recipe;
    private BigDecimal nbPerson;

    public MenuLine(Recipe recipe, BigDecimal nbPerson) {
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
}
