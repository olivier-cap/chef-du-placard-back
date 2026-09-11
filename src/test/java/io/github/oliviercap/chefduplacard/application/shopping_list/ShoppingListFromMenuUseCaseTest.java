package io.github.oliviercap.chefduplacard.application.shopping_list;

import io.github.oliviercap.chefduplacard.application.htttpresponse.ShoppingListResponse;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IMenuRepository;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IPantryStaplesRepository;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IShoppingListRepository;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IStockRepository;
import io.github.oliviercap.chefduplacard.application.shopping_list.shopping_list_from_menu.ShoppingListFromMenuRequestModel;
import io.github.oliviercap.chefduplacard.application.shopping_list.shopping_list_from_menu.ShoppingListFromMenuUseCase;
import io.github.oliviercap.chefduplacard.application.shopping_list.shopping_list_from_menu.ports.IShoppingListFromMenuOutputPort;
import io.github.oliviercap.chefduplacard.domain.food.Aliment;
import io.github.oliviercap.chefduplacard.domain.food.AlimentId;
import io.github.oliviercap.chefduplacard.domain.food.Ingredient;
import io.github.oliviercap.chefduplacard.domain.food.IngredientId;
import io.github.oliviercap.chefduplacard.domain.menu.Menu;
import io.github.oliviercap.chefduplacard.domain.menu.MenuId;
import io.github.oliviercap.chefduplacard.domain.menu.MenuLine;
import io.github.oliviercap.chefduplacard.domain.menu.MenuLineId;
import io.github.oliviercap.chefduplacard.domain.pantry_staples.PantryStaples;
import io.github.oliviercap.chefduplacard.domain.pantry_staples.PantryStaplesId;
import io.github.oliviercap.chefduplacard.domain.pantry_staples.PantryStaplesLine;
import io.github.oliviercap.chefduplacard.domain.pantry_staples.PantryStaplesLineId;
import io.github.oliviercap.chefduplacard.domain.recipe.Recipe;
import io.github.oliviercap.chefduplacard.domain.recipe.RecipeId;
import io.github.oliviercap.chefduplacard.domain.shopping_list.ShoppingList;
import io.github.oliviercap.chefduplacard.domain.shopping_list.ShoppingListId;
import io.github.oliviercap.chefduplacard.domain.shopping_list.ShoppingListLine;
import io.github.oliviercap.chefduplacard.domain.shopping_list.ShoppingListLineId;
import io.github.oliviercap.chefduplacard.domain.stock.Stock;
import io.github.oliviercap.chefduplacard.domain.stock.StockId;
import io.github.oliviercap.chefduplacard.domain.stock.StockLine;
import io.github.oliviercap.chefduplacard.domain.stock.StockLineId;
import io.github.oliviercap.chefduplacard.domain.unit.Unit;
import io.github.oliviercap.chefduplacard.domain.unit.UnitId;
import io.github.oliviercap.chefduplacard.domain.user.User;
import io.github.oliviercap.chefduplacard.domain.user.UserId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShoppingListFromMenuUseCaseTest {

    @Mock
    private IShoppingListRepository shoppingListRepository;

    @Mock
    private IShoppingListFromMenuOutputPort outputPort;

    @Mock
    private IMenuRepository menuRepository;

    @Mock
    private IStockRepository stockRepository;

    @Mock
    private IPantryStaplesRepository pantryStaplesRepository;

    @InjectMocks
    private ShoppingListFromMenuUseCase useCase;

    @Test
    @SuppressWarnings("unchecked")
    void shouldCreateShoppingListFromMenuStockAndPantryStaples() {
        // Given
        Long userId = 1L;
        Long menuId = 10L;
        Long stockId = 20L;
        Long pantryStaplesId = 30L;

        User user = new User(
                new UserId(userId),
                "olivier",
                "olivier@example.com",
                false
        );

        Aliment flour = new Aliment(
                new AlimentId(1L),
                "Farine",
                "Farine de ble",
                true,
                Set.of()
        );

        Unit gram = new Unit(
                new UnitId(1L),
                "gramme",
                "g"
        );

        // La recette necessite 100 g de farine par personne.
        Ingredient recipeIngredient = new Ingredient(
                new IngredientId(1L),
                new BigDecimal("100"),
                flour,
                gram
        );

        Recipe recipe = new Recipe(
                new RecipeId(1L),
                "Recette de test",
                "Instructions de la recette de test",
                Duration.ofMinutes(30),
                "facile",
                List.of(recipeIngredient),
                null
        );

        // Besoin du menu : 100 g x 4 personnes = 400 g.
        MenuLine menuLine = new MenuLine(
                new MenuLineId(1L),
                recipe,
                new BigDecimal("4"),
                LocalDate.of(2026, 9, 11),
                null
        );

        Menu menu = new Menu(
                new MenuId(menuId),
                "Menu de test",
                user,
                List.of(menuLine)
        );

        // Le fond de placard impose 200 g, soit un besoin total de 600 g.
        PantryStaplesLine pantryStaplesLine = new PantryStaplesLine(
                new PantryStaplesLineId(1L),
                flour,
                gram,
                new BigDecimal("200")
        );

        PantryStaples pantryStaples = new PantryStaples(
                true,
                user,
                new PantryStaplesId(pantryStaplesId),
                List.of(pantryStaplesLine)
        );

        // Le stock contient 250 g. Il manque donc 600 - 250 = 350 g.
        StockLine stockLine = new StockLine(
                new StockLineId(1L),
                new BigDecimal("250"),
                flour,
                gram
        );

        Stock stock = new Stock(
                new StockId(stockId),
                "Stock principal",
                List.of(stockLine),
                user
        );

        // Le repository renvoie une liste sauvegardee avec des identifiants.
        ShoppingListLine savedShoppingListLine = new ShoppingListLine(
                new ShoppingListLineId(1L),
                flour,
                gram,
                new BigDecimal("350")
        );

        ShoppingList savedShoppingList = new ShoppingList(
                new ShoppingListId(1L),
                user,
                LocalDate.of(2026, 9, 11),
                List.of(savedShoppingListLine)
        );

        when(menuRepository.findById(menuId))
                .thenReturn(Optional.of(menu));

        when(stockRepository.findById(stockId))
                .thenReturn(Optional.of(stock));

        when(pantryStaplesRepository.findBydId(pantryStaplesId))
                .thenReturn(Optional.of(pantryStaples));

        when(shoppingListRepository.createNewFromShoppingListLines(
                eq(userId),
                anyList()
        )).thenReturn(savedShoppingList);

        ShoppingListFromMenuRequestModel request =
                new ShoppingListFromMenuRequestModel(
                        userId,
                        menuId,
                        stockId,
                        pantryStaplesId
                );

        // When
        useCase.execute(request);

        // Then
        ArgumentCaptor<List<ShoppingListLine>> linesCaptor =
                ArgumentCaptor.forClass(List.class);

        verify(shoppingListRepository)
                .createNewFromShoppingListLines(
                        eq(userId),
                        linesCaptor.capture()
                );

        List<ShoppingListLine> capturedLines = linesCaptor.getValue();

        assertThat(capturedLines).hasSize(1);

        ShoppingListLine capturedLine = capturedLines.getFirst();

        assertThat(capturedLine.getId()).isNull();
        assertThat(capturedLine.getAliment()).isEqualTo(flour);
        assertThat(capturedLine.getUnit()).isEqualTo(gram);
        assertThat(capturedLine.getQuantity())
                .isEqualByComparingTo("350");

        verify(outputPort).displayShoppingList(
                ShoppingListResponse.from(savedShoppingList)
        );

        // La recette originale ne doit pas avoir ete modifiee par le use case.
        assertThat(recipeIngredient.getQuantity())
                .isEqualByComparingTo("100");
    }
}