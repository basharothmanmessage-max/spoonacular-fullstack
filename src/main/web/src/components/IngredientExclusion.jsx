import React, { useState, useEffect } from 'react';
import { recalculateCalories } from '../api/recipeApi';

const IngredientExclusion = ({ recipeId, ingredients, initialCalories }) => {
  const [excludedIds, setExcludedIds] = useState([]);
  const [currentCalories, setCurrentCalories] = useState(initialCalories);
  const [loading, setLoading] = useState(false);

  // Effect to recalculate calories whenever excludedIds changes
  useEffect(() => {
    if (excludedIds.length === 0 && currentCalories !== initialCalories) {
      setCurrentCalories(initialCalories);
      return;
    }

    if (excludedIds.length > 0) {
      setLoading(true);
      recalculateCalories(recipeId, excludedIds)
        .then(response => {
          setCurrentCalories(response.newTotalCalories);
        })
        .catch(err => {
          console.error("Recalculation failed:", err);
          setCurrentCalories("Recalculation Error");
        })
        .finally(() => {
          setLoading(false);
        });
    }
  }, [excludedIds, recipeId, initialCalories]);

  const handleCheckboxChange = (id) => {
    setExcludedIds(prevIds => 
      prevIds.includes(id) 
        ? prevIds.filter(prevId => prevId !== id)
        : [...prevIds, id]
    );
  };

  return (
    <div style={{ border: '1px solid #007bff', padding: '15px', borderRadius: '8px', marginTop: '20px' }}>
      <h3>Nutrition & Ingredient Exclusion</h3>
      <p style={{ fontSize: '1.2em', fontWeight: 'bold' }}>
        Total Calories (Adjusted): {loading ? 'Recalculating...' : currentCalories}
      </p>

      <h4>Ingredients: (Check to exclude from calorie count)</h4>
      <ul style={{ listStyleType: 'none', padding: 0 }}>
        {ingredients.map((ing) => (
          <li key={ing.id} style={{ marginBottom: '5px' }}>
            <input
              type="checkbox"
              id={`ing-${ing.id}`}
              value={ing.id}
              checked={excludedIds.includes(ing.id)}
              onChange={() => handleCheckboxChange(ing.id)}
            />
            <label htmlFor={`ing-${ing.id}`} style={{ marginLeft: '8px' }}>
              {ing.originalString}
            </label>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default IngredientExclusion;