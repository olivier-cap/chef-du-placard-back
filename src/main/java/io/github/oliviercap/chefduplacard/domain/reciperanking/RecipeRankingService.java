package io.github.oliviercap.chefduplacard.domain.reciperanking;

import io.github.oliviercap.chefduplacard.domain.recipe.Recipe;

import java.util.List;

/**
 * Application de règles de classement des recettes en fonction de différents critères, par ex. éviter perte d'aliments
 */
public class RecipeRankingService {

    //PAS DE REGLE DE SELECTION ACTUELLEMENT
    public Recipe selectBestRecipe(List<Recipe> recipes){
        //PAS DE REGLE DE SELECTION ACTUELLEMENT, ON PREND SIMPLEMENT LA PREMIERE DE LA LISTE
        return recipes.getFirst();
    }
}
