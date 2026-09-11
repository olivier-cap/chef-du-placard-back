package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.shopping_list;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.ShoppingListJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IShoppingListJpaRepository extends JpaRepository<ShoppingListJpa, Long> {

    @Query("""
    select sl
    from ShoppingListJpa sl
    left join fetch sl.userJpa
    left join fetch sl.shoppingListLineJpaList line
    left join fetch line.alimentJpa
    left join fetch line.unitJpa
    """)
    Optional<ShoppingListJpa> findCompleteById(@Param("id")Long shoppingListId);
}
