

package com.spoon.data.model;

import java.util.List;
import lombok.Data;

/**
 *
 * @author User
 */
@Data 
public class SpoonacularSearchResponse {
    private List<RecipeSearchResult> results;
    private int totalResults;  
}