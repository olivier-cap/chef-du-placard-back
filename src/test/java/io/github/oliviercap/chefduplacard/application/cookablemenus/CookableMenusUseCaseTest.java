package io.github.oliviercap.chefduplacard.application.cookablemenus;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.RecipeJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.stock.UpdateStockDTO;
import io.github.oliviercap.chefduplacard.application.menu.cookablemenus.CookableMenusRequestModel;
import io.github.oliviercap.chefduplacard.application.menu.cookablemenus.CookableMenusResponseModel;
import io.github.oliviercap.chefduplacard.application.menu.cookablemenus.CookableMenusUseCase;
import io.github.oliviercap.chefduplacard.application.menu.cookablemenus.ports.ICookableMenusOutputPort;
import io.github.oliviercap.chefduplacard.application.recipes.createnewrecipe.IngredientsData;
import io.github.oliviercap.chefduplacard.application.htttpresponse.RecipeResponse;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IRecipeRepository;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IStockRepository;
import io.github.oliviercap.chefduplacard.domain.food.Aliment;
import io.github.oliviercap.chefduplacard.domain.food.AlimentId;
import io.github.oliviercap.chefduplacard.domain.food.Ingredient;
import io.github.oliviercap.chefduplacard.domain.food.IngredientId;
import io.github.oliviercap.chefduplacard.domain.recipe.Recipe;
import io.github.oliviercap.chefduplacard.domain.recipe.RecipeId;
import io.github.oliviercap.chefduplacard.domain.recipe_type.RecipeType;
import io.github.oliviercap.chefduplacard.domain.recipe_type.RecipeTypeId;
import io.github.oliviercap.chefduplacard.domain.stock.Stock;
import io.github.oliviercap.chefduplacard.domain.stock.StockId;
import io.github.oliviercap.chefduplacard.domain.stock.StockLine;
import io.github.oliviercap.chefduplacard.domain.stock.StockLineId;
import io.github.oliviercap.chefduplacard.domain.unit.Unit;
import io.github.oliviercap.chefduplacard.domain.unit.UnitId;
import io.github.oliviercap.chefduplacard.domain.user.User;
import io.github.oliviercap.chefduplacard.domain.user.UserId;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class FakeRecipeRepository implements IRecipeRepository {

    private final RecipeType recipeType = new RecipeType(
            new RecipeTypeId(1L),
            "test recipe type"
    );

    private final Aliment aliment1 = new Aliment(new AlimentId(1L), "apple", "fruit", true);
    private final Aliment aliment2 = new Aliment(new AlimentId(1L), "orange", "fruit", true);
    private final Aliment aliment3 = new Aliment(new AlimentId(1L), "grapefruit", "fruit", true);
    private final Aliment aliment4 = new Aliment(new AlimentId(1L), "cherry", "fruit", true);
    private final Aliment aliment5 = new Aliment(new AlimentId(1L), "apricot", "fruit", true);
    private final Aliment aliment6 = new Aliment(new AlimentId(1L), "pear", "fruit", true);
    private final Aliment aliment7 = new Aliment(new AlimentId(1L), "plum", "fruit", true);

    private final Unit unit = new Unit(new UnitId(1L), "gramme", "g");

    private final Ingredient ingredient1 = new Ingredient(
            new IngredientId(1L), BigDecimal.valueOf(3), aliment1, unit
    );
    private final Ingredient ingredient2 = new Ingredient(
            new IngredientId(1L), BigDecimal.valueOf(8), aliment2, unit
    );
    private final Recipe recipe1 = new Recipe(
            new RecipeId(1L),
            "r1",
            "a",
            Duration.ofMinutes(1),
            "1",
            List.of(ingredient1, ingredient2),
            recipeType
    );

    private final Ingredient ingredient3 = new Ingredient(
            new IngredientId(1L), BigDecimal.valueOf(5), aliment3, unit
    );
    private final Recipe recipe2 = new Recipe(
            new RecipeId(1L),
            "r2",
            "b",
            Duration.ofMinutes(1),
            "1",
            List.of(ingredient3),
            recipeType
    );

    private final Ingredient ingredient4 = new Ingredient(
            new IngredientId(1L), BigDecimal.valueOf(2), aliment4, unit
    );
    private final Ingredient ingredient5 = new Ingredient(
            new IngredientId(1L), BigDecimal.valueOf(12), aliment5, unit
    );
    private final Ingredient ingredient6 = new Ingredient(
            new IngredientId(1L), BigDecimal.valueOf(3), aliment6, unit
    );
    private final Recipe recipe3 = new Recipe(
            new RecipeId(1L),
            "r3",
            "a",
            Duration.ofMinutes(1),
            "1",
            List.of(ingredient4, ingredient5, ingredient6),
            recipeType
    );

    private final Ingredient ingredient7 = new Ingredient(
            new IngredientId(1L), BigDecimal.valueOf(4), aliment7, unit
    );
    private final Recipe recipe4 = new Recipe(
            new RecipeId(1L),
            "r4",
            "a",
            Duration.ofMinutes(1),
            "1",
            List.of(ingredient7),
            recipeType
    );

    private final Ingredient ingredient2_2 = new Ingredient(
            new IngredientId(1L), BigDecimal.valueOf(5), aliment2, unit
    );
    private final Ingredient ingredient4_2 = new Ingredient(
            new IngredientId(1L), BigDecimal.valueOf(23), aliment4, unit
    );
    private final Recipe recipe5 = new Recipe(
            new RecipeId(1L),
            "r5",
            "a",
            Duration.ofMinutes(1),
            "1",
            List.of(ingredient2_2, ingredient4_2),
            recipeType
    );

    private final Ingredient ingredient2_3 = new Ingredient(
            new IngredientId(1L), BigDecimal.valueOf(8), aliment2, unit
    );
    private final Ingredient ingredient4_3 = new Ingredient(
            new IngredientId(1L), BigDecimal.valueOf(2), aliment4, unit
    );
    private final Ingredient ingredient6_3 = new Ingredient(
            new IngredientId(1L), BigDecimal.valueOf(3), aliment6, unit
    );
    private final Recipe recipe6 = new Recipe(
            new RecipeId(1L),
            "r6",
            "a",
            Duration.ofMinutes(1),
            "1",
            List.of(ingredient2_3, ingredient4_3, ingredient6_3),
            recipeType
    );

    private final Ingredient ingredient3_4 = new Ingredient(
            new IngredientId(1L), BigDecimal.ONE, aliment3, unit
    );
    private final Ingredient ingredient5_4 = new Ingredient(
            new IngredientId(1L), BigDecimal.valueOf(9), aliment5, unit
    );
    private final Recipe recipe7 = new Recipe(
            new RecipeId(1L),
            "r7",
            "a",
            Duration.ofMinutes(1),
            "1",
            List.of(ingredient3_4, ingredient5_4),
            recipeType
    );

    @Override
    public List<Recipe> findAll() {
        return List.of(
                recipe1,
                recipe2,
                recipe3,
                recipe4,
                recipe5,
                recipe6,
                recipe7
        );
    }

    @Override
    public Optional<Recipe> findById(Long recipeId) {
        return Optional.empty();
    }

    @Override
    public Optional<RecipeJpa> findJpaById(Long id) {
        return Optional.empty();
    }

    @Override
    public void saveNew(
            String nameRecipe,
            String instructionsRecipe,
            Duration durationRecipe,
            String difficultyRecipe,
            List<IngredientsData> ingredients
    ) {
        // Not used by this test.
    }

    @Override
    public RecipeJpa getReferenceJpaById(Long id) {
        return null;
    }
}

class FakeStockRepository implements IStockRepository {

    private final User owner = new User(
            new UserId(1L),
            "test-user",
            "test-user@example.com",
            false
    );

    private final Aliment aliment1 = new Aliment(new AlimentId(1L), "apple", "fruit", true);
    private final Aliment aliment2 = new Aliment(new AlimentId(1L), "orange", "fruit", true);
    private final Aliment aliment3 = new Aliment(new AlimentId(1L), "grapefruit", "fruit", true);
    private final Aliment aliment4 = new Aliment(new AlimentId(1L), "cherry", "fruit", true);
    private final Aliment aliment5 = new Aliment(new AlimentId(1L), "apricot", "fruit", true);
    private final Aliment aliment6 = new Aliment(new AlimentId(1L), "pear", "fruit", true);
    private final Aliment aliment7 = new Aliment(new AlimentId(1L), "plum", "fruit", true);

    private final Unit unit = new Unit(new UnitId(1L), "gramme", "g");

    private final StockLine stockLine1 = new StockLine(
            new StockLineId(1L), BigDecimal.ZERO, aliment1, unit
    );
    private final StockLine stockLine2 = new StockLine(
            new StockLineId(1L), BigDecimal.valueOf(18), aliment2, unit
    );
    private final StockLine stockLine3 = new StockLine(
            new StockLineId(1L), BigDecimal.valueOf(7), aliment3, unit
    );
    private final StockLine stockLine4 = new StockLine(
            new StockLineId(1L), BigDecimal.valueOf(27), aliment4, unit
    );
    private final StockLine stockLine5 = new StockLine(
            new StockLineId(1L), BigDecimal.valueOf(21), aliment5, unit
    );
    private final StockLine stockLine6 = new StockLine(
            new StockLineId(1L), BigDecimal.valueOf(6), aliment6, unit
    );
    private final StockLine stockLine7 = new StockLine(
            new StockLineId(1L), BigDecimal.ZERO, aliment7, unit
    );

    @Override
    public Optional<Stock> findById(Long stockId) {
        Stock stock = new Stock(
                new StockId(1L),
                "test",
                List.of(
                        stockLine1,
                        stockLine2,
                        stockLine3,
                        stockLine4,
                        stockLine5,
                        stockLine6,
                        stockLine7
                ),
                owner
        );

        return Optional.of(stock);
    }

    @Override
    public void save(Stock stock, User user) {
        // Not used by this test.
    }

    @Override
    public Stock updateStock(UpdateStockDTO updateStockDTO) {
        return null;
    }
}

class CapturingOutputPort implements ICookableMenusOutputPort {

    CookableMenusResponseModel capturedResponse;

    @Override
    public void displayCookableMenus(
            CookableMenusResponseModel cookableMenusResponseModel
    ) {
        this.capturedResponse = cookableMenusResponseModel;
    }
}

class CookableMenusUseCaseTest {

    @Test
    void create_menu_with_stock_without_filters_without_ranking() {
        IRecipeRepository recipeRepository = new FakeRecipeRepository();
        IStockRepository stockRepository = new FakeStockRepository();
        CapturingOutputPort outputPort = new CapturingOutputPort();

        CookableMenusUseCase useCase = new CookableMenusUseCase(
                recipeRepository,
                stockRepository,
                outputPort
        );

        useCase.execute(new CookableMenusRequestModel(
                1L,
                4,
                1,
                List.of()
        ));

        assertThat(outputPort.capturedResponse).isNotNull();
        assertThat(outputPort.capturedResponse.nbMealCovered()).isTrue();
        assertThat(outputPort.capturedResponse.recipes())
                .extracting(RecipeResponse::name)
                .containsExactlyInAnyOrder("r2", "r3", "r5", "r6");
        assertThat(outputPort.capturedResponse.message())
                .isEqualTo("nbmeal recipes founded");
    }
}