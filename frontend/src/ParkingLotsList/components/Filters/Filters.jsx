// import React from 'react';
import React, { useState, useEffect } from 'react';

import styles from './Filters.module.css';
import TypesFilter from './TypesFilter';
import RatingFilter from './RatingFilter';
import LocationFilter from './LocationFilter';

const Filters = () => {
  const [filters, setFilters] = useState({
    types: [],
    minSize: 0,
    maxSize: 100,
    rating: 0,
    location: 'New York',
  });
  const updateFilters= (newFilters) => {

    console.log(newFilters);
    setFilters({ ...filters, ...newFilters });
    console.log(filters);
  }
  const handleApplyFilters = () => {
  };
  const handleRestFilters = () => {
  };
  return (
    <div className={styles.container}>
      <TypesFilter 
        selected={filters.types}
        onChange={(types) => updateFilters({types: types})}
      />
      <LocationFilter location={filters.location} onChange={(location) => updateFilters({location: location})}/>

      <RatingFilter
        rating={filters.rating}
        onChange={(rating) => updateFilters({rating: rating})}
      />
      <button
        onClick={handleApplyFilters}
        className={styles.applyButton}
      >
        Apply 
      </button>
      <button
        onClick={handleRestFilters}
        className={styles.applyButton}
      >
        Reset 
      </button>
      {/* <Pagination /> */}
    </div>
  );
};

export default Filters;