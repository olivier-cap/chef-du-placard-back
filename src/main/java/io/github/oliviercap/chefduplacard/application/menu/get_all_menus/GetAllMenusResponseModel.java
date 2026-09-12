package io.github.oliviercap.chefduplacard.application.menu.get_all_menus;

import io.github.oliviercap.chefduplacard.application.htttpresponse.MenuResponse;

import java.util.List;

public record GetAllMenusResponseModel(List<MenuResponse> menuResponseList) {
}
