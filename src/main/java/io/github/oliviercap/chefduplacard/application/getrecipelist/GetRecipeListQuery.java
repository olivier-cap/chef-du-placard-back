package io.github.oliviercap.chefduplacard.application.getrecipelist;

public record GetRecipeListQuery(String name,
                                 Integer duration,
                                 String difficulty) {
}
