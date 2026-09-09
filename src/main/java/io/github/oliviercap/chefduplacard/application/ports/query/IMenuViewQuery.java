package io.github.oliviercap.chefduplacard.application.ports.query;

import io.github.oliviercap.chefduplacard.application.menu.getmenu.GetMenuQuery;

import java.util.List;

public interface IMenuViewQuery {
    List<GetMenuQuery> getViewMenu(Long menuId);
}
