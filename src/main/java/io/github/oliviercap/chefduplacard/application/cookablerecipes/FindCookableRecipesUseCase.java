package io.github.oliviercap.chefduplacard.application.cookablerecipes;

import io.github.oliviercap.chefduplacard.application.cookablerecipes.ports.IFindCookableRecipesInputPort;
import io.github.oliviercap.chefduplacard.application.cookablerecipes.ports.IFindCookableRecipesOutputPort;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IRecipeRepository;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IStockRepository;
import io.github.oliviercap.chefduplacard.application.htttpresponse.RecipeResponse;
import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import io.github.oliviercap.chefduplacard.domain.food.Ingredient;
import io.github.oliviercap.chefduplacard.domain.recipe.Recipe;
import io.github.oliviercap.chefduplacard.domain.stock.CoveredIngredients;
import io.github.oliviercap.chefduplacard.domain.stock.Stock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Use case de recherche des recettes réalisables avec le stock actuel.
 * Produit un ensemble de recettes individuellement réalisable : pas de prise en compte du fait qu'on en fait 3
 */
public class FindCookableRecipesUseCase implements IFindCookableRecipesInputPort {

    private final IRecipeRepository recipeRepository;
    private final IStockRepository stockRepository;
    private final IFindCookableRecipesOutputPort outputPort;

    public FindCookableRecipesUseCase(IRecipeRepository recipeRepository,
                                      IStockRepository stockRepository,
                                      IFindCookableRecipesOutputPort outputPort
    ) {
        this.recipeRepository = recipeRepository;
        this.stockRepository = stockRepository;
        this.outputPort = outputPort;
    }

    public void execute(FindCookableRecipesRequestModel findCookableRecipesRequestModel) {

        int nbPeople = findCookableRecipesRequestModel.npPeople();
        List<Recipe> cookableRecipes = findCookableRecipes(nbPeople, findCookableRecipesRequestModel.stockId());

        outputPort.displayCookableRecipes(
                new FindCookableRecipesResponseModel(
                        cookableRecipes.stream()
                                .map(RecipeResponse::from)
                                .toList()
                )
        );
    }

    /**
     * Recherche les recettes réalisables pour nbPeople.
     * @param nbPeople nombre de personnes prise en compte pour la recette
     * @return liste de recettes que le stock permet de faire avec nbPeople. Pas forcément possible de réaliser toutes ces recettes.
     */
    private List<Recipe> findCookableRecipes(int nbPeople, Long stockId) {

        List<Recipe> coveredRecipes = new ArrayList<>();

        List<Recipe> existingRecipes = recipeRepository.findAll();
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new DomainException(
                        "Stock " + stockId + " not found"
                ));

        for(Recipe recipe : existingRecipes) {
            List<Ingredient> requiredIngredients = recipe.computeRequiredIngredients(nbPeople);

            CoveredIngredients coveredIngredients = stock.covers(requiredIngredients);

            if(coveredIngredients.covered()) {
                coveredRecipes.add(recipe);
            }
        }

        return coveredRecipes;
    }

}
