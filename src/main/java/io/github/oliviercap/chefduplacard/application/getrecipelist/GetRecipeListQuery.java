package io.github.oliviercap.chefduplacard.application.getrecipelist;

public record GetRecipeListQuery(Long id,
                                 String name,
                                 Integer duration,
                                 String difficulty) {
}
