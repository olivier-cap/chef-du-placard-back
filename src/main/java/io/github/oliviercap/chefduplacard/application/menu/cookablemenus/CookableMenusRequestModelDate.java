package io.github.oliviercap.chefduplacard.application.menu.cookablemenus;


import java.time.LocalDate;
import java.util.List;

public record CookableMenusRequestModelDate(
        Long stockId,
        List<menuPerDayRequest> menuPerDayRequestList
) {
    public record menuPerDayRequest(
            LocalDate date,
            Integer nbPeople,
            boolean breakfast,
            boolean lunch,
            boolean diner
    ) {}
}
