package io.github.oliviercap.chefduplacard.application.htttpresponse;

import io.github.oliviercap.chefduplacard.domain.menu.Menu;

import java.util.List;

public record MenuResponse(
        String menuName,
        List<MenuLineResponse> menuLineResponseList
) {
    public static MenuResponse from(Menu menu){
        return new MenuResponse(
                menu.getName(),
                menu.getMenuLines().stream()
                        .map(MenuLineResponse::from)
                        .toList()
        );
    }
}
