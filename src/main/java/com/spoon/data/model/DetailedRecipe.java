package com.spoon.data.model;

import java.util.List;

/**
 *
 * @author User
 */
public record DetailedRecipe(
        Long id,
        String title,
        String imageUrl,
        String instructions,
        String totalCalories,
        List<IngredientDto> ingredients
        ) {

}
