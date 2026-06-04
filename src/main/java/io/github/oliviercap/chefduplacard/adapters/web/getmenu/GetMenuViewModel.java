package io.github.oliviercap.chefduplacard.adapters.web.getmenu;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

public record GetMenuViewModel(
    String menuName,
    List<MenuLineViewModel> menuLineViewModels
) {
    public record MenuLineViewModel(
            BigDecimal nbPerson,
            RecipeViewModel recipeViewModel
    ){}
    public record RecipeViewModel(
            String name,
            String instructions,
            Duration duration,
            String difficulty
    ){}
}
