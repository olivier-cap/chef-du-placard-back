package io.github.oliviercap.chefduplacard.application.getmenu;

import io.github.oliviercap.chefduplacard.application.htttpresponse.MenuResponse;

import java.util.List;

public record GetMenuResponseModel(List<GetMenuQuery> getMenuQuery) {
}