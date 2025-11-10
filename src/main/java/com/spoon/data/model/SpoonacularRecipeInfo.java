package com.spoon.data.model;

import java.util.List;
import lombok.Data;

/**
 *
 * @author User
 */
@Data
public class SpoonacularRecipeInfo {

    private Long id;
    private String title;
    private String image;
    private String instructions;
    private List<ExtendedIngredient> extendedIngredients;
    private Nutrition nutrition;
}
