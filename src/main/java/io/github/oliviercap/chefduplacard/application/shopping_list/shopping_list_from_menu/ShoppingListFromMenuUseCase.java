package io.github.oliviercap.chefduplacard.application.shopping_list.shopping_list_from_menu;

import io.github.oliviercap.chefduplacard.application.htttpresponse.ShoppingListResponse;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IMenuRepository;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IPantryStaplesRepository;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IShoppingListRepository;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IStockRepository;
import io.github.oliviercap.chefduplacard.application.shopping_list.shopping_list_from_menu.ports.IShoppingListFromMenuInputPort;
import io.github.oliviercap.chefduplacard.application.shopping_list.shopping_list_from_menu.ports.IShoppingListFromMenuOutputPort;
import io.github.oliviercap.chefduplacard.domain.exceptions.DomainException;
import io.github.oliviercap.chefduplacard.domain.food.Ingredient;
import io.github.oliviercap.chefduplacard.domain.menu.Menu;
import io.github.oliviercap.chefduplacard.domain.menu.MenuLine;
import io.github.oliviercap.chefduplacard.domain.pantry_staples.PantryStaples;
import io.github.oliviercap.chefduplacard.domain.pantry_staples.PantryStaplesLine;
import io.github.oliviercap.chefduplacard.domain.shopping_list.ShoppingList;
import io.github.oliviercap.chefduplacard.domain.shopping_list.ShoppingListLine;
import io.github.oliviercap.chefduplacard.domain.stock.CoveredIngredients;
import io.github.oliviercap.chefduplacard.domain.stock.Stock;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListFromMenuUseCase implements IShoppingListFromMenuInputPort {

    private final IShoppingListRepository repository;
    private final IShoppingListFromMenuOutputPort outputPort;
    private final IMenuRepository menuRepository;
    private final IStockRepository stockRepository;
    private final IPantryStaplesRepository pantryStaplesRepository;

    public ShoppingListFromMenuUseCase(
            IShoppingListRepository repository,
            IShoppingListFromMenuOutputPort outputPort,
            IMenuRepository menuRepository,
            IStockRepository stockRepository,
            IPantryStaplesRepository pantryStaplesRepository
    ) {
        this.repository = repository;
        this.outputPort = outputPort;
        this.menuRepository = menuRepository;
        this.stockRepository = stockRepository;
        this.pantryStaplesRepository = pantryStaplesRepository;
    }


    @Override
    public void execute(ShoppingListFromMenuRequestModel requestModel) {
        ShoppingList shoppingList = shoppingListFromMenu(
                requestModel.userId(),
                requestModel.menuId(),
                requestModel.stockId(),
                requestModel.pantryStaplesId()
        );

        outputPort.displayShoppingList(
                ShoppingListResponse.from(shoppingList)
        );

    }

    private ShoppingList shoppingListFromMenu(Long userId, Long menuId, Long stockId, Long pantryStaplesId) {

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new DomainException("menu not found"));

        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new DomainException("stock not found"));

        PantryStaples pantryStaples = pantryStaplesRepository.findBydId(pantryStaplesId)
                .orElseThrow(() -> new DomainException("pantryStaples not found"));

        List<Ingredient> ingredientsList = new ArrayList<>();

        for (MenuLine menuLine : menu.getMenuLines()) {
            ingredientsList.addAll(
                    menuLine.getRecipe().computeRequiredIngredients(
                            menuLine.getNbPerson().intValueExact()
                    )
            );
        }

        //Création d'une liste "d'ingrédients" pour calcul par le stock des aliments manquants
        for(PantryStaplesLine pantryStaplesLine : pantryStaples.getPantryStaplesLineMap().values()) {
            Ingredient temp = new Ingredient(
                    pantryStaplesLine.getQuantity(),
                    pantryStaplesLine.getAliment(),
                    pantryStaplesLine.getUnit()
            );

            ingredientsList.add(temp);
        }

        //Aggrégation des aliments et vérification quantités par le stock
        CoveredIngredients uncovered = stock.covers(ingredientsList);

        //Attention : création de shoppinglist lines sans id (plutôt qu'un id temporaire)
        List<ShoppingListLine> shoppingListLines = uncovered.uncoveredIngredients().stream()
                .map(u ->
                        new ShoppingListLine(
                                u.ingredient().getAliment(),
                                u.ingredient().getUnit(),
                                u.missingQuantity()
                        )
                ).toList();

        //Sauvegarde de la nouvelle shoppingList à partir de la liste de shoppingListLine
        try {
            return repository.createNewFromShoppingListLines(userId, shoppingListLines);
        } catch (Exception e) {
            throw new DomainException("error save new shoppinglist",e);
        }
    }

}
