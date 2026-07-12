package io.github.oliviercap.chefduplacard.application.createnewrecipe;

import java.time.Duration;
import java.util.List;

public record CreateNewRecipeRequestModel(String name,
                                          String instructions,
                                          Duration duration,
                                          String difficulty,
                                          List<IngredientsData> ingredients) {
}
