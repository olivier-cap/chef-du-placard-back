package io.github.oliviercap.chefduplacard.adapters.web.findcookablerecipes.controllers;

import io.github.oliviercap.chefduplacard.adapters.web.findcookablerecipes.FindCookableRecipesRequestModel;
import io.github.oliviercap.chefduplacard.adapters.web.findcookablerecipes.FindCookableRecipesResponseModel;
import io.github.oliviercap.chefduplacard.adapters.web.findcookablerecipes.presenters.dto.RecipeForPresenter;
import io.github.oliviercap.chefduplacard.application.cookablerecipes.IFindCookableRecipesInputPort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Duration;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FindCookableRecipesController.class)
public class FindCookableRecipesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IFindCookableRecipesInputPort inputPort;

    @Test
    void should_return_cookable_recipes() throws Exception {

        RecipeForPresenter recipe = new RecipeForPresenter(
                "r1",
                "instructions",
                Duration.ofMinutes(5),
                "easy",
                List.of()
        );

        FindCookableRecipesResponseModel response =
                new FindCookableRecipesResponseModel(List.of(recipe));

        when(inputPort.execute(any(FindCookableRecipesRequestModel.class)))
                .thenReturn(response);

        mockMvc.perform(get("/findCookableRecipes")
                        .param("nbPeople", "1")
                        .param("stock", "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipes[0].recipeName").value("r1"));
    }
}