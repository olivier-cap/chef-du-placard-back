package io.github.oliviercap.chefduplacard.application.getmenu;

import java.math.BigDecimal;


public record GetMenuQuery(Long menuId,
                           String menuName,
                           BigDecimal nbPerson,
                           String name,
                           String instructions,
                           Integer duration,
                           String difficulty
){}
