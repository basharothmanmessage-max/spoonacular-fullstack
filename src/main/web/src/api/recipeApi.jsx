
import axios from 'axios';

const API_BASE_URL = 'http://localhost:8090/api/v1/recipes';

const recipeApi = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

export const searchRecipes = async (query, page = 0, size = 10, filters = {}) => {
  const params = {
    query,
    page,
    size,
    ...filters,
  };
  const response = await recipeApi.get('/search', { params });
  return response.data;
};

export const getRecipeDetails = async (id) => {
  const response = await recipeApi.get(`/${id}`);
  return response.data;
};

export const recalculateCalories = async (recipeId, excludedIngredientIds) => {
  const response = await recipeApi.post('/recalculate', {
    recipeId,
    excludedIngredientIds,
  });
  return response.data;
};