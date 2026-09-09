package io.github.oliviercap.chefduplacard.application.menu.savenewmenu;

import io.github.oliviercap.chefduplacard.adapters.web.menu.savenewmenu.controllers.SaveNewMenuRequest;

public record SaveNewMenuRequestModel(SaveNewMenuRequest newMenuRecord,Long userId) {
}
