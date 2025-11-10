package com.spoon.helper;

import com.spoon.data.model.DetailedRecipe;
import com.spoon.data.model.IngredientDto;
import com.spoon.data.model.Nutrient;
import com.spoon.data.model.RecalculationResponse;
import com.spoon.data.model.SpoonacularRecipeInfo;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author User
 */
@Component
public class RecipeMapper {

    public DetailedRecipe mapToDetailedRecipe(SpoonacularRecipeInfo info) {

        List<IngredientDto> ingredients = info.getExtendedIngredients().stream()
                .map(ing -> new IngredientDto(ing.getId(), ing.getName(), ing.getOriginal()))
                .toList();

        String calories = extractTotalCalories(info.getNutrition().getNutrients());

        return new DetailedRecipe(
                info.getId(),
                info.getTitle(),
                info.getImage(),
                info.getInstructions(),
                calories,
                ingredients
        );
    }

     public String extractTotalCalories(List<Nutrient> nutrients) {
        return nutrients.stream()
                .filter(n -> "Calories".equalsIgnoreCase(n.getName()))
                .findFirst()
                .map(n -> String.format("%.0f %s", n.getAmount(), n.getUnit()))
                .orElse("N/A");
    }

     public RecalculationResponse recalculateCalories(SpoonacularRecipeInfo info, List<Long> excludedIds) {
        String totalCaloriesStr = extractTotalCalories(info.getNutrition().getNutrients());

        if ("N/A".equals(totalCaloriesStr)) {
            return new RecalculationResponse("N/A (Recalculation failed)");
        }
          try {
            double totalCalories = Double.parseDouble(totalCaloriesStr.split(" ")[0]);
            double subtractedCalories = (double) excludedIds.size() * 50.0;
            double newCalories = Math.max(0, totalCalories - subtractedCalories);

            return new RecalculationResponse(String.format("%.0f kcal (adjusted)", newCalories));
        } catch (NumberFormatException e) {
             return new RecalculationResponse("N/A (Data format error)");
        }
    }
}
