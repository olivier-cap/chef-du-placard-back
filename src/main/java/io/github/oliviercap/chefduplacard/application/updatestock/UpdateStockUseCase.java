package io.github.oliviercap.chefduplacard.application.updatestock;

import io.github.oliviercap.chefduplacard.application.ports.persistence.IRecipeRepository;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IStockRepository;
import io.github.oliviercap.chefduplacard.application.updatestock.port.IUpdateStockInputPort;
import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import io.github.oliviercap.chefduplacard.domain.food.Ingredient;
import io.github.oliviercap.chefduplacard.domain.recipe.Recipe;
import io.github.oliviercap.chefduplacard.domain.stock.Stock;

import java.util.List;

/**
 * Mis à jour du stock REEL lorsqu'une recette - ou qu'un ensemble de recettes - est déclaré "réalisé" par l'utilisateur
 * Mets à jour le stock reel dans la base de données
 */
public class UpdateStockUseCase implements IUpdateStockInputPort {

    private final IRecipeRepository recipeRepository;
    private final IStockRepository stockRepository;

    public UpdateStockUseCase(IRecipeRepository recipeRepository,
                              IStockRepository stockRepository) {
        this.recipeRepository = recipeRepository;
        this.stockRepository = stockRepository;
    }


    @Override
    public void execute(UpdateStockRequestModel requestModel) {

    }

    //Necessite ecriture du stock dans base de données
    //Necessite verification/transaction de cette action: soit stock maj, soit non :)
    //envoie true si stock effectivement maj
    private UpdateStockResponseModel updateStockByRecipe(String recipeName, int nbPeople, String stockName) {
        String responseMessage;
        boolean sufficientStock;

        Recipe recipe = recipeRepository.findByName(recipeName).orElseThrow(() -> new DomainException("RecipeNotFound"));

        Stock stock = stockRepository.findByName(stockName).orElseThrow(() -> new DomainException("Stock not found"));

        //Calcul de la quantité d'ingrédients nécessaires pour nbPeople
        List<Ingredient> requiredIngredients = recipe.computeRequiredIngredients(nbPeople);

        //Consommation des ingrédients dans le stock
        //m.a.j même si le stock est insuffisant
        //Dans ce usecase on considère que l'utilisateur indique qu'il a fait la recette
        //Si le stock n'a pas été correctement mis à jour, on met les aliments à 0
        sufficientStock = stock.consume(requiredIngredients);

        //Enregistrement message stock suffisant/insuffisant
        if(sufficientStock) {
            responseMessage = "Stock Updated, sufficient initial stock";
        } else {
            responseMessage = "Stock Corrected, insufficient initial stock";
        }

        //si le commit de la sauvegarde du stock ne passe pas, on soulève une erreur
        try {
            stockRepository.save(stock);
        } catch (Exception e) {
            throw new DomainException("stock save did not succeeded",e);
        }

        return new UpdateStockResponseModel(sufficientStock, responseMessage);
    }

}
