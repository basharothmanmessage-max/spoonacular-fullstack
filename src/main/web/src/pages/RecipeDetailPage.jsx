import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { getRecipeDetails } from '../api/recipeApi';
import IngredientExclusion from '../components/IngredientExclusion';

const RecipeDetailPage = () => {
  const { id } = useParams();
  const [recipe, setRecipe] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    setLoading(true);
    setError(null);
    getRecipeDetails(id)
      .then(data => {
        setRecipe(data);
      })
      .catch(err => {
        console.error(err);
        if (err.response && err.response.status === 404) {
             setError("Recipe not found. It may have been removed.");
        } else {
             setError("Failed to fetch recipe details.");
        }
      })
      .finally(() => {
        setLoading(false);
      });
  }, [id]);

  if (loading) return <div style={{ textAlign: 'center', marginTop: '50px' }}>Loading recipe details...</div>;
  if (error) return <div style={{ color: 'red', textAlign: 'center', marginTop: '50px' }}>Error: {error}</div>;
  if (!recipe) return null; // Should be handled by loading/error, but good fallback

  // Clean the instructions for display (basic)
  const cleanInstructions = recipe.instructions ? recipe.instructions.replace(/<\/?ol>/g, '').replace(/<\/?li>/g, ' • ') : 'No instructions provided.';

  return (
    <div style={{ maxWidth: '1000px', margin: '20px auto', padding: '20px', backgroundColor: '#f9f9f9', borderRadius: '10px' }}>
      <h1>{recipe.title}</h1>
      
      <div style={{ display: 'flex', gap: '30px', flexWrap: 'wrap' }}>
        <img src={recipe.imageUrl} alt={recipe.title} style={{ width: '100%', maxWidth: '400px', height: 'auto', borderRadius: '8px' }} />
        
        <div style={{ flexGrow: 1, minWidth: '300px' }}>
          {/* Component for Calorie Recalculation */}
          <IngredientExclusion 
            recipeId={recipe.id}
            ingredients={recipe.ingredients}
            initialCalories={recipe.totalCalories}
          />
        </div>
      </div>

      <div style={{ marginTop: '30px' }}>
        <h2>Instructions</h2>
        <p style={{ lineHeight: '1.6' }}>{cleanInstructions}</p>
      </div>
      
      <div style={{ marginTop: '30px' }}>
         <button onClick={() => window.history.back()} style={{ padding: '10px 20px', backgroundColor: '#6c757d', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer' }}>
             ← Back to Search
         </button>
      </div>
    </div>
  );
};

export default RecipeDetailPage;