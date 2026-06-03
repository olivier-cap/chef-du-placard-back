package io.github.oliviercap.chefduplacard.application.dto;

import java.util.List;

public record MenuResponse(
        String menuName,
        List<MenuLineResponse> menuLineResponseList
) {
}
