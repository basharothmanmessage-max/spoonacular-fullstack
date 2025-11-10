import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { searchRecipes } from '../api/recipeApi';
import SearchBar from '../components/SearchBar';
import PaginationControls from '../components/PaginationControls';

const RecipeSearchPage = () => {
  const [query, setQuery] = useState('');
  const [results, setResults] = useState(null); // PagedRecipeResponse object
  const [currentPage, setCurrentPage] = useState(0);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const SIZE = 10; // Page size

  const fetchRecipes = async (searchQuery, page) => {
    if (!searchQuery) return;
    setLoading(true);
    setError(null);
    try {
      const data = await searchRecipes(searchQuery, page, SIZE);
      setResults(data);
    } catch (err) {
      console.error(err);
      setError('Failed to fetch recipes. Check API key or backend connection.');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchRecipes(query, currentPage);
  }, [query, currentPage]);

  const handleSearch = (newQuery) => {
    if (newQuery !== query) {
      setQuery(newQuery);
      setCurrentPage(0); // Reset page on new search
    } else {
      fetchRecipes(newQuery, currentPage);
    }
  };

  const handlePageChange = (newPage) => {
    setCurrentPage(newPage);
  };

  return (
    <div style={{ maxWidth: '1200px', margin: '0 auto', padding: '20px' }}>
      <h2>Recipe Search</h2>
      <SearchBar onSearch={handleSearch} />

      {loading && <p>Loading recipes...</p>}
      {error && <p style={{ color: 'red' }}>Error: {error}</p>}

      {results && results.recipes.length > 0 && (
        <>
          <p>Found {results.totalResults} results.</p>
          <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(300px, 1fr))', gap: '20px' }}>
            {results.recipes.map((recipe) => (
              <div key={recipe.id} style={{ border: '1px solid #ddd', padding: '15px', borderRadius: '8px' }}>
                <img src={recipe.image} alt={recipe.title} style={{ width: '100%', height: '200px', objectFit: 'cover', borderRadius: '4px' }} />
                <h4>{recipe.title}</h4>
                <Link to={`/recipe/${recipe.id}`} style={{ display: 'block', marginTop: '10px', color: '#007bff' }}>
                  View Details
                </Link>
              </div>
            ))}
          </div>
          
          <PaginationControls 
            currentPage={results.currentPage} 
            totalPages={results.totalPages} 
            onPageChange={handlePageChange} 
          />
        </>
      )}

      {results && results.recipes.length === 0 && query && !loading && <p>No recipes found for "{query}".</p>}
    </div>
  );
};

export default RecipeSearchPage;