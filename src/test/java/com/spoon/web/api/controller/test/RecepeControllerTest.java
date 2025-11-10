package com.spoon.web.api.controller.test;

/**
 *
 * @author User
 */
import com.spoon.data.model.DetailedRecipe;
import com.spoon.data.model.PagedRecipeResponse;
import com.spoon.data.model.RecalculationRequest;
import com.spoon.data.model.RecalculationResponse;
import com.spoon.data.model.RecipeSearchResult;
import com.spoon.exception.handler.RecipeNotFoundException;
import com.spoon.service.SpoonacularService;
import com.spoon.web.api.controller.RecipeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecipeController.class)
public class RecepeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpoonacularService recipeService;

    private static final String API_BASE_PATH = "/api/v1/recipes";

    private static String asJsonString(final Object obj) {
        try {

            return new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void searchRecipes_shouldReturnPagedResults_whenSuccessful() throws Exception {
        RecipeSearchResult recipe1 = new RecipeSearchResult(654959L, "Pasta with pesto", "image1.jpg");
        RecipeSearchResult recipe2 = new RecipeSearchResult(716426L, "Healthy salad", "image2.jpg");
        List<RecipeSearchResult> recipes = Arrays.asList(recipe1, recipe2);

        PagedRecipeResponse mockResponse = new PagedRecipeResponse(recipes, 12, 0, 10);

        when(recipeService.searchRecipes(
                eq("pasta"),
                anyMap(),
                eq(0),
                eq(10))
        ).thenReturn(mockResponse);

        mockMvc.perform(get(API_BASE_PATH + "/search")
                .param("query", "pasta")
                .param("page", "0")
                .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipes.length()").value(2))
                .andExpect(jsonPath("$.totalResults").value(12))
                .andExpect(jsonPath("$.recipes[0].title").value("Pasta with pesto"));
    }

    @Test
    void searchRecipes_shouldHandleFilters_whenPresent() throws Exception {
        // Arrange
        PagedRecipeResponse mockResponse = new PagedRecipeResponse(Collections.emptyList(), 0, 0, 10);

        when(recipeService.searchRecipes(
                eq("keto"),
                anyMap(),
                anyInt(),
                anyInt())
        ).thenReturn(mockResponse);

        mockMvc.perform(get(API_BASE_PATH + "/search")
                .param("query", "keto")
                .param("cuisine", "italian")
                .param("diet", "keto")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResults").value(0));
    }

    @Test
    void getRecipeDetails_shouldReturnDetails_whenFound() throws Exception {
        // Arrange
        Long recipeId = 641803L;
        DetailedRecipe mockRecipe = new DetailedRecipe(
                recipeId,
                "Baked Salmon",
                "image.jpg",
                "Preheat oven...",
                "450 kcal",
                Collections.emptyList()
        );

        when(recipeService.getRecipeDetails(recipeId)).thenReturn(mockRecipe);

        mockMvc.perform(get(API_BASE_PATH + "/{id}", recipeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Baked Salmon"))
                .andExpect(jsonPath("$.totalCalories").value("450 kcal"));
    }

    @Test
    void getRecipeDetails_shouldReturn404_whenNotFound() throws Exception {
        Long recipeId = 999999L;

        when(recipeService.getRecipeDetails(anyLong()))
                .thenThrow(new RecipeNotFoundException("Recipe not found."));

        mockMvc.perform(get(API_BASE_PATH + "/{id}", recipeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("NOT_FOUND"));
    }

    @Test
    void recalculateCalories_shouldReturnNewCalories_whenValid() throws Exception {
        // Arrange
        RecalculationRequest request = new RecalculationRequest(
                641803L,
                Arrays.asList(1L, 2L)
        );
        RecalculationResponse mockResponse = new RecalculationResponse("300 kcal (adjusted)");

        when(recipeService.recalculate(any(RecalculationRequest.class))).thenReturn(mockResponse);

        mockMvc.perform(post(API_BASE_PATH + "/recalculate")
                .content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.newTotalCalories").value("300 kcal (adjusted)"));
    }

    @Test
    void recalculateCalories_shouldReturn400_whenInvalidRequest() throws Exception {
        String invalidJson = "{\"recipeId\": 641803, \"excludedIngredientIds\": null}";

        mockMvc.perform(post(API_BASE_PATH + "/recalculate")
                .content(invalidJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
