// src/App.js

import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import RecipeSearchPage from './pages/RecipeSearchPage';
import RecipeDetailPage from './pages/RecipeDetailPage';

function App() {
  return (
    <Router>
      <header style={{ backgroundColor: '#f0f0f0', padding: '15px', textAlign: 'center', borderBottom: '2px solid #ccc' }}>
        <h1>Spoonacular Recipe Explorer</h1>
      </header>
      <Routes>
        <Route path="/" element={<RecipeSearchPage />} />
        <Route path="/recipe/:id" element={<RecipeDetailPage />} />
      </Routes>
    </Router>
  );
}

export default App;