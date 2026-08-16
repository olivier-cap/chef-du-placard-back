package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.query.recipe;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.RecipeJpa;
import io.github.oliviercap.chefduplacard.application.getrecipelist.GetRecipeListQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IGetRecipeListJpaQuery extends JpaRepository<RecipeJpa, Long> {

    @Query("""
        select new io.github.oliviercap.chefduplacard.application.getrecipelist.GetRecipeListQuery(
                r.id,
                r.name,
                r.durationMinutes,
                r.difficulty
            )
        from RecipeJpa r
    """)
    List<GetRecipeListQuery> getRecipeView();
}
