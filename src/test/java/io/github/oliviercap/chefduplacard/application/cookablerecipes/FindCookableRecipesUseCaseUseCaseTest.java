package io.github.oliviercap.chefduplacard.application.cookablerecipes;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.RecipeJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.stock.UpdateStockDTO;
import io.github.oliviercap.chefduplacard.application.recipes.cookablerecipes.FindCookableRecipesRequestModel;
import io.github.oliviercap.chefduplacard.application.recipes.cookablerecipes.FindCookableRecipesResponseModel;
import io.github.oliviercap.chefduplacard.application.recipes.cookablerecipes.FindCookableRecipesUseCase;
import io.github.oliviercap.chefduplacard.application.recipes.cookablerecipes.ports.IFindCookableRecipesOutputPort;
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

    private final Aliment aliment1 = new Aliment(
            new AlimentId(1L), "apple", "fruit", true
    );
    private final Aliment aliment2 = new Aliment(
            new AlimentId(1L), "orange", "fruit", true
    );
    private final Aliment aliment3 = new Aliment(
            new AlimentId(1L), "grapefruit", "fruit", true
    );

    private final Unit unit = new Unit(
            new UnitId(1L), "gramme", "g"
    );

    private final Ingredient ingredient1 = new Ingredient(
            new IngredientId(1L), BigDecimal.valueOf(3), aliment1, unit
    );
    private final Ingredient ingredient2 = new Ingredient(
            new IngredientId(1L), BigDecimal.valueOf(8), aliment2, unit
    );
    private final Ingredient ingredient3 = new Ingredient(
            new IngredientId(1L), BigDecimal.valueOf(5), aliment3, unit
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

    private final Recipe recipe2 = new Recipe(
            new RecipeId(1L),
            "r2",
            "b",
            Duration.ofMinutes(1),
            "1",
            List.of(ingredient3),
            recipeType
    );

    @Override
    public List<Recipe> findAll() {
        return List.of(recipe1, recipe2);
    }

    @Override
    public Optional<Recipe> findById(Long recipeId) {
        return Optional.empty();
    }

    @Override
    public Optional<RecipeJpa> findJpaById(Long recipeId) {
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

    private final Aliment aliment1 = new Aliment(
            new AlimentId(1L), "apple", "fruit", true
    );
    private final Aliment aliment2 = new Aliment(
            new AlimentId(1L), "orange", "fruit", true
    );
    private final Aliment aliment3 = new Aliment(
            new AlimentId(1L), "grapefruit", "fruit", true
    );

    private final Unit unit = new Unit(
            new UnitId(1L), "gramme", "g"
    );

    private final StockLine stockLine1 = new StockLine(
            new StockLineId(1L), BigDecimal.valueOf(5), aliment1, unit
    );
    private final StockLine stockLine2 = new StockLine(
            new StockLineId(1L), BigDecimal.valueOf(12), aliment2, unit
    );
    private final StockLine stockLine3 = new StockLine(
            new StockLineId(1L), BigDecimal.ZERO, aliment3, unit
    );

    @Override
    public Optional<Stock> findById(Long id) {
        Stock stock = new Stock(
                new StockId(1L),
                "test",
                List.of(stockLine1, stockLine2, stockLine3),
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

class CapturingOutputPort implements IFindCookableRecipesOutputPort {

    FindCookableRecipesResponseModel capturedResponse;

    @Override
    public void displayCookableRecipes(
            FindCookableRecipesResponseModel responseModel
    ) {
        this.capturedResponse = responseModel;
    }
}

class FindCookableRecipesUseCaseUseCaseTest {

    @Test
    void returns_only_recipes_fully_cookable_with_given_stock() {
        IRecipeRepository recipeRepository = new FakeRecipeRepository();
        IStockRepository stockRepository = new FakeStockRepository();
        CapturingOutputPort outputPort = new CapturingOutputPort();

        FindCookableRecipesUseCase useCase = new FindCookableRecipesUseCase(
                recipeRepository,
                stockRepository,
                outputPort
        );

        useCase.execute(new FindCookableRecipesRequestModel(1, 1L));

        assertThat(outputPort.capturedResponse).isNotNull();
        assertThat(outputPort.capturedResponse.recipeResponses())
                .extracting(RecipeResponse::name)
                .containsExactly("r1");
    }

    @Test
    void aliments_with_same_name_and_description_are_equal() {
        Aliment apple1 = new Aliment(
                new AlimentId(1L), "apple", "fruit", true
        );
        Aliment apple2 = new Aliment(
                new AlimentId(1L), "apple", "fruit", true
        );

        assertThat(apple1).isEqualTo(apple2);
        assertThat(apple1.hashCode()).isEqualTo(apple2.hashCode());
    }
}