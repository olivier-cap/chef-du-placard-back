package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.shopping_list;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.ShoppingListJpa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IShoppingListJpaRepository extends JpaRepository<ShoppingListJpa, Long> {
}
