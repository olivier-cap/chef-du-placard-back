package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.shopping_list_line;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.ShoppingListJpa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IShoppingListLineJpaRepository extends JpaRepository<ShoppingListJpa, Long> {
}
