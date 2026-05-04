package io.github.oliviercap.chefduplacard.application.cookablerecipes;

import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.recipe.IRecipeRepository;
import io.github.oliviercap.chefduplacard.adapters.persistence.jpa.repository.stock.IStockRepository;
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
    private Aliment aliment1 = new Aliment("fruit","apple",true);
    private Aliment aliment2 = new Aliment("fruit","orange",true);
    private Aliment aliment3 = new Aliment("fruit","grapefruit",true);

    Unit unit = new Unit("gramme","g");

    private Ingredient ingredient1 = new Ingredient(BigDecimal.valueOf(3), aliment1, unit);
    private Ingredient ingredient2 = new Ingredient(BigDecimal.valueOf(8), aliment2, unit);
    private Ingredient ingredient3 = new Ingredient(BigDecimal.valueOf(5), aliment3, unit);

    private Recipe recipe1 = new Recipe("r1", "", Duration.ofMinutes(1),"1", List.of(ingredient1, ingredient2));
    private Recipe recipe2 = new Recipe("r2", "", Duration.ofMinutes(1),"1", List.of(ingredient3));

    @Override
    public List<Recipe> findAll() {
        return List.of(recipe1, recipe2);
    }
}

class FakeStockRepository implements IStockRepository {
    private Aliment aliment1 = new Aliment("fruit","apple",true);
    private Aliment aliment2 = new Aliment("fruit","orange",true);
    private Aliment aliment3 = new Aliment("fruit","grapefruit",true);

    Unit unit = new Unit("gramme","g");

    private StockLine ingredient1 = new StockLine(BigDecimal.valueOf(5), aliment1, unit);
    private StockLine ingredient2 = new StockLine(BigDecimal.valueOf(12), aliment2, unit);
    private StockLine ingredient3 = new StockLine(BigDecimal.valueOf(0), aliment3, unit);


    @Override
    public Optional<Stock> findByName(String name) {
        return Optional.of(new Stock("test", List.of(ingredient1, ingredient2, ingredient3)));
    }
}


public class FindCookableRecipesUseCaseTest {

    @Test
    void find_a_cookable_recipe_without_UnitChange_without_DBConnection() {
        //Given
        IRecipeRepository fakeRecipeRepository = new FakeRecipeRepository();
        IStockRepository fakeStockRepository = new FakeStockRepository();


        //With
        FindCookableRecipesUseCase findCookableRecipesUseCase = new FindCookableRecipesUseCase(fakeRecipeRepository, fakeStockRepository);

        //Then
        int nbPeople = 1;

        List<Recipe> attendedResult = List.of(fakeRecipeRepository.findAll().getFirst());

        assertThat(findCookableRecipesUseCase.execute(nbPeople))
                .usingRecursiveComparison()
                .isEqualTo(attendedResult);
    }

    @Test
    void aliments_with_same_name_and_description_are_equal() {
        Aliment apple1 = new Aliment("fruit", "apple", true);
        Aliment apple2 = new Aliment("fruit", "apple", true);

        assertThat(apple1).isEqualTo(apple2);
        assertThat(apple1.hashCode()).isEqualTo(apple2.hashCode());
    }
}
