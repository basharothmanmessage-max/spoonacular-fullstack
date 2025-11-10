package com.spoon.service;

/**
 *
 * @author User
 */
import com.spoon.data.model.DetailedRecipe;
import com.spoon.data.model.PagedRecipeResponse;
import com.spoon.data.model.RecalculationRequest;
import com.spoon.data.model.RecalculationResponse;
import com.spoon.data.model.RecipeSearchResult;
import com.spoon.data.model.SpoonacularRecipeInfo;
import com.spoon.data.model.SpoonacularSearchResponse;
import com.spoon.helper.RecipeMapper;
import com.spoon.web.client.SpoonacularFeignClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SpoonacularService {

    @Value("${spoonacular.api-key}")
    private String apiKey;

    private final SpoonacularFeignClient feignClient;
    private final RecipeMapper mapper;

    public SpoonacularService(SpoonacularFeignClient feignClient, RecipeMapper mapper) {
        this.feignClient = feignClient;
        this.mapper = mapper;
    }

    public PagedRecipeResponse searchRecipes(String query, Map<String, String> rawFilters, int page, int size) {

        if (size > 50) {
            size = 50;
        }
        int offset = page * size;

        Map<String, Object> feignFilters = new HashMap<>(rawFilters);

        SpoonacularSearchResponse response = feignClient.searchRecipes(
                query,
                feignFilters,
                apiKey,
                size,
                offset
        );

        List<RecipeSearchResult> results = response.getResults();

        return new PagedRecipeResponse(
                results,
                response.getTotalResults(),
                page,
                size
        );
    }

    public DetailedRecipe getRecipeDetails(Long id) {
        SpoonacularRecipeInfo info = feignClient.getRecipeInfo(id, true, apiKey);
        return mapper.mapToDetailedRecipe(info);
    }

    public RecalculationResponse recalculate(RecalculationRequest request) {
        SpoonacularRecipeInfo info = feignClient.getRecipeInfo(request.recipeId(), true, apiKey);

        return mapper.recalculateCalories(info, request.excludedIngredientIds());
    }
}
