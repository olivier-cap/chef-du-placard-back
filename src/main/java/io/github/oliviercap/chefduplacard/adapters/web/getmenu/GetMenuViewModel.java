package io.github.oliviercap.chefduplacard.adapters.web.getmenu;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

public record GetMenuViewModel(
        Long menuId,
        String menuName,
        List<MenuLineViewModel> menuLineViewModels
) {
    public record MenuLineViewModel(
            Long menuLineId,
            BigDecimal nbPerson,
            RecipeViewModel recipeViewModel
    ){}
    public record RecipeViewModel(
            Long recipeId,
            String name,
            String instructions,
            Duration duration,
            String difficulty
    ){}
}
