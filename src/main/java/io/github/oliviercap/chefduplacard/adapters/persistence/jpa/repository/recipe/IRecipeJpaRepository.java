package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.recipe;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.RecipeJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IRecipeJpaRepository extends JpaRepository<RecipeJpa, Long> {
    @Query("""
        select distinct r
        from RecipeJpa r
        left join fetch r.ingredients i
            left join fetch i.alimentJpa
            left join fetch i.unitJpa
    """)
    List<RecipeJpa> findAllComplete();

    @Query("""
        select distinct r
        from RecipeJpa r
        left join fetch r.ingredients i
            left join fetch i.alimentJpa
            left join fetch i.unitJpa
        where r.name = :recipeName
    """)
    Optional<RecipeJpa> findCompleteByName(@Param("recipeName") String recipeName);

    @Query("""
        select distinct r
        from RecipeJpa r
        left join fetch r.ingredients i
            left join fetch i.alimentJpa
            left join fetch i.unitJpa
        where r.name = :recipeID
    """)
    Optional<RecipeJpa> findCompleteById(@Param("recipeID") Long recipeID);
}
