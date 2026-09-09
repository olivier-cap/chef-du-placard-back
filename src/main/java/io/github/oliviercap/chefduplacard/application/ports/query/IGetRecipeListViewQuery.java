package io.github.oliviercap.chefduplacard.application.ports.query;

import io.github.oliviercap.chefduplacard.application.recipes.getrecipelist.GetRecipeListQuery;

import java.util.List;

public interface IGetRecipeListViewQuery {
    List<GetRecipeListQuery> getRecipeListQuery();
}
