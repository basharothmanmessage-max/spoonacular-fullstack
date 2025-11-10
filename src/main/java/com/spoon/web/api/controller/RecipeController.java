package com.spoon.web.api.controller;

import com.spoon.data.model.DetailedRecipe;
import com.spoon.data.model.PagedRecipeResponse;
import com.spoon.data.model.RecalculationRequest;
import com.spoon.data.model.RecalculationResponse;
import com.spoon.service.SpoonacularService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 *
 * @author User
 */
@RestController
@RequestMapping("/api/v1/recipes")
@Tag(name = "Recipe Gateway", description = "Endpoints for searching and viewing recipe details.")
public class RecipeController {

    private final SpoonacularService recipeService;

    public RecipeController(SpoonacularService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/search")
    @Operation(summary = "Search recipes with pagination and filters",
            description = "Queries the Spoonacular API, supporting 'query', filters, and pagination (page & size).")
    @ApiResponse(responseCode = "200", description = "Successful retrieval.")
    public ResponseEntity<PagedRecipeResponse> searchRecipes(
            @RequestParam String query,
            @RequestParam(required = false) Map<String, String> filters,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

       

        PagedRecipeResponse results = recipeService.searchRecipes(query, filters, page, size);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get detailed recipe information and initial calories")
    @ApiResponse(responseCode = "200", description = "Successful retrieval.")
    @ApiResponse(responseCode = "404", description = "Recipe not found (via custom handler).")
    public ResponseEntity<DetailedRecipe> getRecipeDetails(@PathVariable Long id) {
        DetailedRecipe recipe = recipeService.getRecipeDetails(id);
        return ResponseEntity.ok(recipe);
    }

    @PostMapping("/recalculate")
    @Operation(summary = "Recalculate calories based on ingredient exclusion")
    @ApiResponse(responseCode = "200", description = "Successful recalculation.")
    @ApiResponse(responseCode = "400", description = "Invalid request data (via validation).")
    public ResponseEntity<RecalculationResponse> recalculateCalories(
            @Valid @RequestBody RecalculationRequest request) {

        RecalculationResponse response = recipeService.recalculate(request);
        return ResponseEntity.ok(response);
    }
}
