package io.github.oliviercap.chefduplacard.application.cookablerecipes;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.recipe.IRecipeRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.stock.IStockRepository;
import io.github.oliviercap.chefduplacard.adapters.web.findcookablerecipes.FindCookableRecipesRequestModel;
import io.github.oliviercap.chefduplacard.adapters.web.findcookablerecipes.FindCookableRecipesResponseModel;
import io.github.oliviercap.chefduplacard.adapters.web.findcookablerecipes.presenters.IFindCookableRecipesOutputPort;
import io.github.oliviercap.chefduplacard.application.converter.reciperesponse.IRecipeToRecipeResponse;
import io.github.oliviercap.chefduplacard.domain.food.Ingredient;
import io.github.oliviercap.chefduplacard.domain.recipe.Recipe;
import io.github.oliviercap.chefduplacard.domain.stock.CoveredIngredients;
import io.github.oliviercap.chefduplacard.domain.stock.Stock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//public class FindCookableRecipesUseCase implements IUseCasePort{
public class FindCookableRecipesUseCase implements IFindCookableRecipesInputPort {

    private final IRecipeRepository recipeRepository;
    private final IStockRepository stockRepository;
    private final IFindCookableRecipesOutputPort outputPort;
    private final IRecipeToRecipeResponse recipeToRecipeResponse;

    public FindCookableRecipesUseCase(IRecipeRepository recipeRepository,
                                      IStockRepository stockRepository,
                                      IFindCookableRecipesOutputPort outputPort,
                                      IRecipeToRecipeResponse recipeToRecipeResponse) {
        this.recipeRepository = recipeRepository;
        this.stockRepository = stockRepository;
        this.outputPort = outputPort;
        this.recipeToRecipeResponse = recipeToRecipeResponse;
    }

    public FindCookableRecipesResponseModel execute(FindCookableRecipesRequestModel findCookableRecipesRequestModel) {

        int nbPeople = findCookableRecipesRequestModel.npPeople();
        List<Recipe> cookableRecipes = findCookableRecipes(nbPeople);

        return outputPort.displayCookableRecipes(
                cookableRecipes.stream()
                        .map(recipeToRecipeResponse::toDTO)
                        .toList()
        );
    }

    private List<Recipe> findCookableRecipes(int nbPeople) {
        /*
        problemes possibles
        pas de existing recipes, liste vide
        NE PAS METTRE LE NOM DU STOCK EN DUR (test ici), LE METTRE EN ARGUMENT DE FINDCOOKABLERECIPES !!
        stock optional non trouvé
        stock optional sans aucune stockline ?
         */

        String stockName = "test";

        List<Recipe> coveredRecipes = new ArrayList<>();

        List<Recipe> existingRecipes = recipeRepository.findAll();
        Optional<Stock> stockOptional = stockRepository.findByName(stockName);
        Stock stock = stockOptional.orElseThrow();

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
