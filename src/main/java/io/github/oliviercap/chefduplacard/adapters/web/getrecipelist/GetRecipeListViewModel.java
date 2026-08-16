package io.github.oliviercap.chefduplacard.adapters.web.getrecipelist;

import java.time.Duration;
import java.util.List;

public record GetRecipeListViewModel(
        List<RecipeList> recipes
) {
    public record RecipeList(
            Long id,
            String name,
            Duration duration,
            String difficulty
    ) {}
}
