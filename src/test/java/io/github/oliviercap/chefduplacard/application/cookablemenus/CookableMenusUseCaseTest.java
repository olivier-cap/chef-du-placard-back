package io.github.oliviercap.chefduplacard.application.cookablemenus;

import io.github.oliviercap.chefduplacard.application.ports.persistence.IRecipeRepository;
import io.github.oliviercap.chefduplacard.application.ports.persistence.IStockRepository;
import io.github.oliviercap.chefduplacard.application.dto.RecipeResponse;
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
    private final Aliment aliment4 = new Aliment("cherry", "fruit", true);
    private final Aliment aliment5 = new Aliment("apricot", "fruit", true);
    private final Aliment aliment6 = new Aliment("pear", "fruit", true);
    private final Aliment aliment7 = new Aliment("plum", "fruit", true);

    private final Unit unit = new Unit("gramme", "g");

    private final Ingredient ingredient1 = new Ingredient(BigDecimal.valueOf(3), aliment1, unit);
    private final Ingredient ingredient2 = new Ingredient(BigDecimal.valueOf(8), aliment2, unit);
    private final Recipe recipe1 = new Recipe("r1", "a", Duration.ofMinutes(1), "1", List.of(ingredient1, ingredient2));

    private final Ingredient ingredient3 = new Ingredient(BigDecimal.valueOf(5), aliment3, unit);
    private final Recipe recipe2 = new Recipe("r2", "b", Duration.ofMinutes(1), "1", List.of(ingredient3));

    private final Ingredient ingredient4 = new Ingredient(BigDecimal.valueOf(2), aliment4, unit);
    private final Ingredient ingredient5 = new Ingredient(BigDecimal.valueOf(12), aliment5, unit);
    private final Ingredient ingredient6 = new Ingredient(BigDecimal.valueOf(3), aliment6, unit);
    private final Recipe recipe3 = new Recipe("r3", "a", Duration.ofMinutes(1), "1", List.of(ingredient4, ingredient5, ingredient6));

    private final Ingredient ingredient7 = new Ingredient(BigDecimal.valueOf(4), aliment7, unit);
    private final Recipe recipe4 = new Recipe("r4", "a", Duration.ofMinutes(1), "1", List.of(ingredient7));

    private final Ingredient ingredient2_2 = new Ingredient(BigDecimal.valueOf(5), aliment2, unit);
    private final Ingredient ingredient4_2 = new Ingredient(BigDecimal.valueOf(23), aliment4, unit);
    private final Recipe recipe5 = new Recipe("r5", "a", Duration.ofMinutes(1), "1", List.of(ingredient2_2, ingredient4_2));

    private final Ingredient ingredient2_3 = new Ingredient(BigDecimal.valueOf(8), aliment2, unit);
    private final Ingredient ingredient4_3 = new Ingredient(BigDecimal.valueOf(2), aliment4, unit);
    private final Ingredient ingredient6_3 = new Ingredient(BigDecimal.valueOf(3), aliment6, unit);
    private final Recipe recipe6 = new Recipe("r6", "a", Duration.ofMinutes(1), "1", List.of(ingredient2_3, ingredient4_3, ingredient6_3));

    private final Ingredient ingredient3_4 = new Ingredient(BigDecimal.valueOf(1), aliment3, unit);
    private final Ingredient ingredient5_4 = new Ingredient(BigDecimal.valueOf(9), aliment5, unit);
    private final Recipe recipe7 = new Recipe("r7", "a", Duration.ofMinutes(1), "1", List.of(ingredient3_4, ingredient5_4));

    @Override
    public List<Recipe> findAll() {
        return List.of(recipe1, recipe2, recipe3, recipe4, recipe5, recipe6, recipe7);
    }
}

class FakeStockRepository implements IStockRepository {
    private final Aliment aliment1 = new Aliment("apple", "fruit", true);
    private final Aliment aliment2 = new Aliment("orange", "fruit", true);
    private final Aliment aliment3 = new Aliment("grapefruit", "fruit", true);
    private final Aliment aliment4 = new Aliment("cherry", "fruit", true);
    private final Aliment aliment5 = new Aliment("apricot", "fruit", true);
    private final Aliment aliment6 = new Aliment("pear", "fruit", true);
    private final Aliment aliment7 = new Aliment("plum", "fruit", true);

    private final Unit unit = new Unit("gramme", "g");

    private final StockLine stockLine1 = new StockLine(BigDecimal.valueOf(0), aliment1, unit);
    private final StockLine stockLine2 = new StockLine(BigDecimal.valueOf(18), aliment2, unit);
    private final StockLine stockLine3 = new StockLine(BigDecimal.valueOf(7), aliment3, unit);
    private final StockLine stockLine4 = new StockLine(BigDecimal.valueOf(27), aliment4, unit);
    private final StockLine stockLine5 = new StockLine(BigDecimal.valueOf(21), aliment5, unit);
    private final StockLine stockLine6 = new StockLine(BigDecimal.valueOf(6), aliment6, unit);
    private final StockLine stockLine7 = new StockLine(BigDecimal.valueOf(0), aliment7, unit);

    @Override
    public Optional<Stock> findByName(String name) {
        return Optional.of(new Stock("test", List.of(
                stockLine1,
                stockLine2,
                stockLine3,
                stockLine4,
                stockLine5,
                stockLine6,
                stockLine7
        )));
    }
}

class CapturingOutputPort implements ICookableMenusOutputPort {
    CookableMenusResponseModel capturedResponse;

    @Override
    public void displayCookableMenus(CookableMenusResponseModel cookableMenusResponseModel) {
        this.capturedResponse = cookableMenusResponseModel;
    }
}

public class CookableMenusUseCaseTest {

    @Test
    void create_menu_with_stock_without_filters_without_ranking() {

        // GIVEN
        IRecipeRepository recipeRepository = new FakeRecipeRepository();
        IStockRepository stockRepository = new FakeStockRepository();
        CapturingOutputPort outputPort = new CapturingOutputPort();

        CookableMenusUseCase useCase =
                new CookableMenusUseCase(
                        recipeRepository,
                        stockRepository,
                        outputPort
                );

        // WHEN
        useCase.execute(new CookableMenusRequestModel(
                "test",
                4,
                1,
                List.of()
        ));

        // THEN
        assertThat(outputPort.capturedResponse).isNotNull();

        assertThat(outputPort.capturedResponse.nbMealCovered()).isTrue();

        assertThat(outputPort.capturedResponse.recipes())
                .extracting(RecipeResponse::name)
                .containsExactlyInAnyOrder("r2", "r3", "r5", "r6");

        assertThat(outputPort.capturedResponse.message())
                .isEqualTo("nbmeal recipes founded");
    }
}