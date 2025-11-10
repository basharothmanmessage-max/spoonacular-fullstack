import React, { useState } from 'react';

const SearchBar = ({ onSearch }) => {
  const [query, setQuery] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    if (query.trim()) {
      onSearch(query.trim());
    }
  };

  return (
    <form onSubmit={handleSubmit} style={{ margin: '20px 0', textAlign: 'center' }}>
      <label htmlFor="recipe-query" className="visually-hidden">Search Recipes</label>
      <input
        id="recipe-query"
        type="text"
        placeholder="Search for a recipe (e.g., pasta, chicken)"
        value={query}
        onChange={(e) => setQuery(e.target.value)}
        style={{ padding: '10px', width: '60%', marginRight: '10px', borderRadius: '4px', border: '1px solid #ccc' }}
      />
      <button type="submit" style={{ padding: '10px 20px', backgroundColor: '#007bff', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer' }}>
        Search
      </button>
    </form>
  );
};

export default SearchBar;