package io.github.oliviercap.chefduplacard.application.cookablerecipes;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.recipe.IRecipeRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.stock.IStockRepository;
import io.github.oliviercap.chefduplacard.domain.food.Ingredient;
import io.github.oliviercap.chefduplacard.domain.recipe.Recipe;
import io.github.oliviercap.chefduplacard.domain.stock.CoveredIngredients;
import io.github.oliviercap.chefduplacard.domain.stock.Stock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//public class FindCookableRecipesUseCase implements IUseCasePort{
public class FindCookableRecipesUseCase implements IFindCookableRecipesUseCase{

    private IRecipeRepository recipeRepository;
    private IStockRepository stockRepository;

    public FindCookableRecipesUseCase(IRecipeRepository recipeRepository, IStockRepository stockRepository) {
        this.recipeRepository = recipeRepository;
        this.stockRepository = stockRepository;
    }

    //public void execute(IRequestModel requestModel) {
    public List<Recipe> execute(int nbPeople) {

        /*
        problemes possibles
        nbPeople == 0
        */

        //List<Recipe> cookableRecipes = findCookableRecipes(requestModel.nbPeople);
        List<Recipe> cookableRecipes = findCookableRecipes(nbPeople);
        return cookableRecipes;
        //ResponseModel responseModel = createResponseModel(cookableRecipes);
    }

    private List<Recipe> findCookableRecipes(int nbPeople) {
        /*
        problemes possibles
        pas de existing recipes, liste vide
        NE PAS METTRE LE NOM DU STOCK EN DUR (test ici), LE METTRE EN ARGUMENT DE FINDCOOKABLERECIPES !!
        stock optional non trouvé
        stock optional sans aucune stockline ?
         */

        List<Recipe> coveredRecipes = new ArrayList<>();

        List<Recipe> existingRecipes = recipeRepository.findAll();
        Optional<Stock> stockOptional = stockRepository.findByName("test");
        Stock stock = stockOptional.orElseThrow();

        for(Recipe recipe : existingRecipes) {
            List<Ingredient> requiredIngredients = recipe.computeRequiredIngredients(nbPeople);

            CoveredIngredients coveredIngredients = stock.covers(requiredIngredients);

            System.out.println(coveredIngredients);

            if(coveredIngredients.covered()) {
                coveredRecipes.add(recipe);
            }
        }

        return coveredRecipes;
    }



}
