package io.github.oliviercap.chefduplacard.application.cookablerecipes;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.RecipeJpa;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.stock.UpdateStockDTO;
import io.github.oliviercap.chefduplacard.application.cookablerecipes.ports.IFindCookableRecipesOutputPort;
import io.github.oliviercap.chefduplacard.application.createnewrecipe.IngredientsData;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IRecipeRepository;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IStockRepository;
import io.github.oliviercap.chefduplacard.application.htttpresponse.RecipeResponse;
import io.github.oliviercap.chefduplacard.domain.food.Aliment;
import io.github.oliviercap.chefduplacard.domain.food.AlimentId;
import io.github.oliviercap.chefduplacard.domain.food.Ingredient;
import io.github.oliviercap.chefduplacard.domain.food.IngredientId;
import io.github.oliviercap.chefduplacard.domain.recipe.Recipe;
import io.github.oliviercap.chefduplacard.domain.recipe.RecipeId;
import io.github.oliviercap.chefduplacard.domain.stock.Stock;
import io.github.oliviercap.chefduplacard.domain.stock.StockId;
import io.github.oliviercap.chefduplacard.domain.stock.StockLine;
import io.github.oliviercap.chefduplacard.domain.stock.StockLineId;
import io.github.oliviercap.chefduplacard.domain.unit.Unit;
import io.github.oliviercap.chefduplacard.domain.unit.UnitId;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class FakeRecipeRepository implements IRecipeRepository {
    private final Aliment aliment1 = new Aliment(new AlimentId(Long.valueOf(1)),"apple", "fruit", true);
    private final Aliment aliment2 = new Aliment(new AlimentId(Long.valueOf(1)),"orange", "fruit", true);
    private final Aliment aliment3 = new Aliment(new AlimentId(Long.valueOf(1)), "grapefruit", "fruit", true);

    private final Unit unit = new Unit(new UnitId(Long.valueOf(1)),"gramme","g");

    private final Ingredient ingredient1 = new Ingredient(new IngredientId(Long.valueOf(1)), BigDecimal.valueOf(3), aliment1, unit);
    private final Ingredient ingredient2 = new Ingredient(new IngredientId(Long.valueOf(1)), BigDecimal.valueOf(8), aliment2, unit);
    private final Ingredient ingredient3 = new Ingredient(new IngredientId(Long.valueOf(1)), BigDecimal.valueOf(5), aliment3, unit);

    private final Recipe recipe1 = new Recipe(new RecipeId(Long.valueOf(1)), "r1", "a", Duration.ofMinutes(1),"1", List.of(ingredient1, ingredient2));
    private final Recipe recipe2 = new Recipe(new RecipeId(Long.valueOf(1)), "r2", "b", Duration.ofMinutes(1),"1", List.of(ingredient3));

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
    public void saveNew(String nameRecipe, String instructionsRecipe, Duration durationRecipe, String difficultyRecipe, List<IngredientsData> ingredients) {

    }

    @Override
    public RecipeJpa getReferenceJpaById(Long id) {
        return null;
    }

}

class FakeStockRepository implements IStockRepository {
    private final Aliment aliment1 = new Aliment(new AlimentId(Long.valueOf(1)),"apple", "fruit", true);
    private final Aliment aliment2 = new Aliment(new AlimentId(Long.valueOf(1)),"orange", "fruit", true);
    private final Aliment aliment3 = new Aliment(new AlimentId(Long.valueOf(1)),"grapefruit", "fruit", true);

    private final Unit unit = new Unit(new UnitId(Long.valueOf(1)),"gramme","g");

    private final StockLine ingredient1 = new StockLine(new StockLineId(Long.valueOf(1)),BigDecimal.valueOf(5), aliment1, unit);
    private final StockLine ingredient2 = new StockLine(new StockLineId(Long.valueOf(1)),BigDecimal.valueOf(12), aliment2, unit);
    private final StockLine ingredient3 = new StockLine(new StockLineId(Long.valueOf(1)),BigDecimal.valueOf(0), aliment3, unit);

    @Override
    public Optional<Stock> findById(Long id) {
        return Optional.of(new Stock(new StockId(Long.valueOf(1)), "test", List.of(ingredient1, ingredient2, ingredient3)));
    }

    @Override
    public void save(Stock stock) {

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
            FindCookableRecipesResponseModel responseModel) {

        this.capturedResponse = responseModel;
    }
}

public class FindCookableRecipesUseCaseUseCaseTest {

    @Test
    void returns_only_recipes_fully_cookable_with_given_stock() {

        // GIVEN
        IRecipeRepository recipeRepository = new FakeRecipeRepository();
        IStockRepository stockRepository = new FakeStockRepository();
        CapturingOutputPort outputPort = new CapturingOutputPort();

        FindCookableRecipesUseCase useCase =
                new FindCookableRecipesUseCase(
                        recipeRepository,
                        stockRepository,
                        outputPort
                );

        // WHEN
        useCase.execute(new FindCookableRecipesRequestModel(1, Long.valueOf(1)));

        // THEN
        assertThat(outputPort.capturedResponse).isNotNull();

        assertThat(outputPort.capturedResponse.recipeResponses())
                .extracting(RecipeResponse::name)
                .containsExactly("r1");
    }

    @Test
    void aliments_with_same_name_and_description_are_equal() {
        Aliment apple1 = new Aliment(new AlimentId(Long.valueOf(1)),"apple", "fruit", true);
        Aliment apple2 = new Aliment(new AlimentId(Long.valueOf(1)),"apple", "fruit", true);

        assertThat(apple1).isEqualTo(apple2);
        assertThat(apple1.hashCode()).isEqualTo(apple2.hashCode());
    }
}