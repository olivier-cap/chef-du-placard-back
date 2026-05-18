package io.github.oliviercap.chefduplacard.adapters.web.findcookablerecipes.controllers;

import io.github.oliviercap.chefduplacard.adapters.web.findcookablerecipes.FindCookableRecipesViewModel;
import io.github.oliviercap.chefduplacard.adapters.web.findcookablerecipes.presenters.FindCookableRecipesPresenter;
import io.github.oliviercap.chefduplacard.application.cookablerecipes.FindCookableRecipesRequestModel;
import io.github.oliviercap.chefduplacard.application.cookablerecipes.IFindCookableRecipesInputPort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Duration;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FindCookableRecipesController.class)
public class FindCookableRecipesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IFindCookableRecipesInputPort inputPort;

    @MockitoBean
    private FindCookableRecipesPresenter presenter;

    @Test
    void should_return_cookable_recipes() throws Exception {

        // GIVEN
        FindCookableRecipesViewModel.RecipeViewModel recipe =
                new FindCookableRecipesViewModel.RecipeViewModel(
                        "r1",
                        "instructions",
                        Duration.ofMinutes(5),
                        "easy",
                        List.of()
                );

        FindCookableRecipesViewModel viewModel =
                new FindCookableRecipesViewModel(
                        List.of(recipe)
                );

        doNothing().when(inputPort)
                .execute(any(FindCookableRecipesRequestModel.class));

        when(presenter.getViewModel())
                .thenReturn(viewModel);

        // WHEN / THEN
        mockMvc.perform(get("/findCookableRecipes")
                        .param("nbPeople", "1")
                        .param("stock", "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipes[0].recipeName").value("r1"))
                .andExpect(jsonPath("$.recipes[0].recipeInstructions").value("instructions"))
                .andExpect(jsonPath("$.recipes[0].duration").value("PT5M"))
                .andExpect(jsonPath("$.recipes[0].difficulty").value("easy"))
                .andExpect(jsonPath("$.recipes[0].ingredients").isArray());

        verify(inputPort)
                .execute(any(FindCookableRecipesRequestModel.class));

        verify(presenter)
                .getViewModel();
    }
}