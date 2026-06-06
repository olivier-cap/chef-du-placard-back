package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.query.recipe;

import io.github.oliviercap.chefduplacard.application.getrecipelist.GetRecipeListQuery;
import io.github.oliviercap.chefduplacard.application.ports.query.IGetRecipeListViewQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GetRecipeListViewQuery implements IGetRecipeListViewQuery{

    private final IGetRecipeListJpaQuery getRecipeListJpaQuery;

    public GetRecipeListViewQuery(IGetRecipeListJpaQuery getRecipeListJpaQuery) {
        this.getRecipeListJpaQuery = getRecipeListJpaQuery;
    }

    public List<GetRecipeListQuery> getRecipeListQuery() {
        return getRecipeListJpaQuery.getRecipeView();
    }
}
