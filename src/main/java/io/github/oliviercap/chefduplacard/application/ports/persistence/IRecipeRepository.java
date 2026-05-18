package io.github.oliviercap.chefduplacard.application.ports.persistence;

import io.github.oliviercap.chefduplacard.domain.recipe.Recipe;

import java.util.List;

public interface IRecipeRepository {
    public List<Recipe> findAll();
}
