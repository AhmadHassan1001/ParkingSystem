import React from 'react';
import './FiltersStyles.css';
import LocationFilter from './LocationFilter';

function Filters({ filters, setFilters }) {
  const updateFilters = (newFilters) => {
    console.log('newFilters', newFilters);
    setFilters({ ...filters, ...newFilters });
  };

  return (
    <div className="filters">
      <LocationFilter location={filters.location} onChange={(location) => updateFilters({ location })} />
      <button onClick={() => setFilters(filters)} className="apply-button">Apply</button>
      <button onClick={() => setFilters({ location: null })} className="reset-button">Reset</button>
    </div>
  );
}

export default Filters;