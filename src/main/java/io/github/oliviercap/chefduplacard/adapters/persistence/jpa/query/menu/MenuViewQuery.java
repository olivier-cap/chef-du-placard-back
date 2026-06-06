package io.github.oliviercap.chefduplacard.adapters.persistence.jpa.query.menu;

import io.github.oliviercap.chefduplacard.application.getmenu.GetMenuQuery;
import io.github.oliviercap.chefduplacard.application.ports.query.IMenuViewQuery;
import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MenuViewQuery implements IMenuViewQuery {

    private final IMenuJpaQuery menuJpaQuery;

    public MenuViewQuery(IMenuJpaQuery menuJpaQuery) {
        this.menuJpaQuery = menuJpaQuery;
    }

    public List<GetMenuQuery> getViewMenu(String menuName){
        if(menuName.isBlank()) {
            throw new DomainException("menuName must not be blank");
        }

        return menuJpaQuery.getViewMenu(menuName);
    }
}
