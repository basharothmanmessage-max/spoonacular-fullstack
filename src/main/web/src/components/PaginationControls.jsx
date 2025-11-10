import React from 'react';

const PaginationControls = ({ currentPage, totalPages, onPageChange }) => {
  if (totalPages <= 1) return null;

  const pages = [...Array(totalPages).keys()]; // [0, 1, 2, ...]

  return (
    <div style={{ margin: '20px 0', textAlign: 'center' }}>
      {pages.map(page => (
        <button
          key={page}
          onClick={() => onPageChange(page)}
          disabled={page === currentPage}
          style={{
            margin: '0 5px',
            padding: '8px 12px',
            backgroundColor: page === currentPage ? '#0056b3' : '#007bff',
            color: 'white',
            border: 'none',
            borderRadius: '4px',
            cursor: 'pointer',
          }}
        >
          {page + 1}
        </button>
      ))}
    </div>
  );
};

export default PaginationControls;