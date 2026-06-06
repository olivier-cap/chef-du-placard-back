package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.query.menu;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.MenuJpa;
import io.github.oliviercap.chefduplacard.application.getmenu.GetMenuQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IMenuJpaQuery extends JpaRepository<MenuJpa, Long> {

    @Query("""
        select new io.github.oliviercap.chefduplacard.application.getmenu.GetMenuQuery(
                m.name,
                ml.nbPerson,
                r.name,
                r.instructions,
                r.durationMinutes,
                r.difficulty
                )
        from MenuJpa m
        left join m.menuLineJpaList ml
            left join ml.recipeJpa r
        where m.name = :menuName
            """)
    List<GetMenuQuery> getViewMenu(@Param("menuName") String menuName);
}
