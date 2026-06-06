package io.github.oliviercap.chefduplacard.application.cookablerecipes;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.JPAentity.RecipeJpa;
import io.github.oliviercap.chefduplacard.application.cookablerecipes.ports.IFindCookableRecipesOutputPort;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IRecipeRepository;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IStockRepository;
import io.github.oliviercap.chefduplacard.application.htttpresponse.RecipeResponse;
import io.github.oliviercap.chefduplacard.domain.food.Aliment;
import io.github.oliviercap.chefduplacard.domain.food.Ingredient;
import io.github.oliviercap.chefduplacard.domain.recipe.Recipe;
import io.github.oliviercap.chefduplacard.domain.stock.Stock;
import io.github.oliviercap.chefduplacard.domain.stock.StockLine;
import io.github.oliviercap.chefduplacard.domain.unit.Unit;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class FakeRecipeRepository implements IRecipeRepository {
    private final Aliment aliment1 = new Aliment("apple", "fruit", true);
    private final Aliment aliment2 = new Aliment("orange", "fruit", true);
    private final Aliment aliment3 = new Aliment("grapefruit", "fruit", true);

    private final Unit unit = new Unit("gramme","g");

    private final Ingredient ingredient1 = new Ingredient(BigDecimal.valueOf(3), aliment1, unit);
    private final Ingredient ingredient2 = new Ingredient(BigDecimal.valueOf(8), aliment2, unit);
    private final Ingredient ingredient3 = new Ingredient(BigDecimal.valueOf(5), aliment3, unit);

    private final Recipe recipe1 = new Recipe("r1", "a", Duration.ofMinutes(1),"1", List.of(ingredient1, ingredient2));
    private final Recipe recipe2 = new Recipe("r2", "b", Duration.ofMinutes(1),"1", List.of(ingredient3));

    @Override
    public List<Recipe> findAll() {
        return List.of(recipe1, recipe2);
    }

    @Override
    public Optional<Recipe> findByName(String recipeName) {
        return Optional.empty();
    }

    @Override
    public Optional<RecipeJpa> findJpaById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<RecipeJpa> findJpaByName(String recipeName) {
        return Optional.empty();
    }
}

class FakeStockRepository implements IStockRepository {
    private final Aliment aliment1 = new Aliment("apple", "fruit", true);
    private final Aliment aliment2 = new Aliment("orange", "fruit", true);
    private final Aliment aliment3 = new Aliment("grapefruit", "fruit", true);

    private final Unit unit = new Unit("gramme","g");

    private final StockLine ingredient1 = new StockLine(BigDecimal.valueOf(5), aliment1, unit);
    private final StockLine ingredient2 = new StockLine(BigDecimal.valueOf(12), aliment2, unit);
    private final StockLine ingredient3 = new StockLine(BigDecimal.valueOf(0), aliment3, unit);

    @Override
    public Optional<Stock> findByName(String name) {
        return Optional.of(new Stock("test", List.of(ingredient1, ingredient2, ingredient3)));
    }

    @Override
    public void save(Stock stock) {

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
        useCase.execute(new FindCookableRecipesRequestModel(1, "test"));

        // THEN
        assertThat(outputPort.capturedResponse).isNotNull();

        assertThat(outputPort.capturedResponse.recipeResponses())
                .extracting(RecipeResponse::name)
                .containsExactly("r1");
    }

    @Test
    void aliments_with_same_name_and_description_are_equal() {
        Aliment apple1 = new Aliment("apple", "fruit", true);
        Aliment apple2 = new Aliment("apple", "fruit", true);

        assertThat(apple1).isEqualTo(apple2);
        assertThat(apple1.hashCode()).isEqualTo(apple2.hashCode());
    }
}