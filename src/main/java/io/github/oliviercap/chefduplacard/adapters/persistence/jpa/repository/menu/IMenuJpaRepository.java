package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.menu;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.MenuJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IMenuJpaRepository extends JpaRepository<MenuJpa, Long> {

    @Query("""
        select distinct m
        from MenuJpa m
        left join fetch m.menuLineJpaList ml
            left join fetch ml.recipeJpa 
        where m.id = :menuId
    """)
    Optional<MenuJpa> findMenuDetailsById(@Param("menuId") Long menuId);
}
