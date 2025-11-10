 
package com.spoon.web.client;

/**
 *
 * @author User
 */
import com.spoon.data.model.SpoonacularRecipeInfo;
import com.spoon.data.model.SpoonacularSearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "spoonacular-api", url = "${spoonacular.base-url}")
public interface SpoonacularFeignClient {

    @GetMapping("/complexSearch")
    SpoonacularSearchResponse searchRecipes(
        @RequestParam("query") String query,
        @RequestParam Map<String, Object> filters,
        @RequestParam("apiKey") String apiKey,
        @RequestParam("number") int number,   // Page size
        @RequestParam("offset") int offset    // Start index
    );

    @GetMapping("/{id}/information")
    SpoonacularRecipeInfo getRecipeInfo(
        @PathVariable("id") Long id,
        @RequestParam("includeNutrition") boolean includeNutrition,
        @RequestParam("apiKey") String apiKey
    );
}