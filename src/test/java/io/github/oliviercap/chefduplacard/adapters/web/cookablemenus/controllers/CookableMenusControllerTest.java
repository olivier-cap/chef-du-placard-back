package io.github.oliviercap.chefduplacard.adapters.web.cookablemenus.controllers;

import io.github.oliviercap.chefduplacard.adapters.web.cookablemenus.CookableMenusViewModel;
import io.github.oliviercap.chefduplacard.adapters.web.cookablemenus.presenters.CookableMenusPresenter;
import io.github.oliviercap.chefduplacard.application.cookablemenus.CookableMenusRequestModel;
import io.github.oliviercap.chefduplacard.application.cookablemenus.ports.ICookableMenusInputPort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Duration;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CookableMenusController.class)
class CookableMenusControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ICookableMenusInputPort inputPort;

    @MockitoBean
    private CookableMenusPresenter presenter;

    @Test
    void should_return_cookable_menu() throws Exception {
        // Given
        CookableMenusViewModel.RecipeViewModel recipe =
                new CookableMenusViewModel.RecipeViewModel(
                        1L,
                        "r1",
                        "instructions",
                        Duration.ofMinutes(5),
                        "easy",
                        List.of()
                );

        CookableMenusViewModel viewModel =
                new CookableMenusViewModel(
                        true,
                        List.of(recipe),
                        "message"
                );

        doNothing().when(inputPort)
                .execute(any(CookableMenusRequestModel.class));

        when(presenter.getViewModel()).thenReturn(viewModel);

        // When and then
        mockMvc.perform(post("/api/cookableMenus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nbPeople": 1,
                                  "nbMeal": 1,
                                  "stockId": 42
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipes[0].recipeName").value("r1"))
                .andExpect(jsonPath("$.recipes[0].recipeInstructions")
                        .value("instructions"))
                .andExpect(jsonPath("$.recipes[0].duration").value("PT5M"))
                .andExpect(jsonPath("$.recipes[0].difficulty").value("easy"))
                .andExpect(jsonPath("$.recipes[0].ingredients").isArray());

        verify(inputPort).execute(
                new CookableMenusRequestModel(
                        42L,
                        1,
                        1,
                        List.of()
                )
        );

        verify(presenter).getViewModel();
    }
}