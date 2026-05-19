package io.github.oliviercap.chefduplacard.adapters.web.cookablemenus.controllers;

import io.github.oliviercap.chefduplacard.adapters.web.cookablemenus.CookableMenusViewModel;
import io.github.oliviercap.chefduplacard.adapters.web.cookablemenus.presenters.CookableMenusPresenter;
import io.github.oliviercap.chefduplacard.application.cookablemenus.CookableMenusRequestModel;
import io.github.oliviercap.chefduplacard.application.cookablemenus.ports.ICookableMenusInputPort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Duration;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CookableMenusController.class)
public class CookableMenusControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ICookableMenusInputPort inputPort;

    @MockitoBean
    private CookableMenusPresenter presenter;


    @Test
    void should_return_cookable_menu() throws Exception {

        // GIVEN
        CookableMenusViewModel.RecipeViewModel recipe =
                new CookableMenusViewModel.RecipeViewModel(
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

        when(presenter.getViewModel())
                .thenReturn(viewModel);

        // WHEN / THEN
        mockMvc.perform(get("/cookableMenus")
                        .param("nbPeople", "1")
                        .param("nbMeal", "1")
                        .param("stockName", "stockname")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipes[0].recipeName").value("r1"))
                .andExpect(jsonPath("$.recipes[0].recipeInstructions").value("instructions"))
                .andExpect(jsonPath("$.recipes[0].duration").value("PT5M"))
                .andExpect(jsonPath("$.recipes[0].difficulty").value("easy"))
                .andExpect(jsonPath("$.recipes[0].ingredients").isArray());

        verify(inputPort)
                .execute(any(CookableMenusRequestModel.class));

        verify(presenter)
                .getViewModel();
    }
}
