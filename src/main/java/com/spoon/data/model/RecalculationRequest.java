 
package com.spoon.data.model;

import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 *
 * @author User
 */
public record RecalculationRequest(
    @NotNull Long recipeId,
    @NotNull List<Long> excludedIngredientIds
) {}