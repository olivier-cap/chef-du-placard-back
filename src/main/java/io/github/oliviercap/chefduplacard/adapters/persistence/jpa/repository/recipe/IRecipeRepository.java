package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.recipe;

import io.github.oliviercap.chefduplacard.domain.recipe.Recipe;

import java.util.List;

public interface IRecipeRepository {
    public List<Recipe> findAll();
}
