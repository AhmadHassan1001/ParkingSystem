import React from 'react';
import './FiltersStyles.css';

function LocationFilter({ location, onChange }) {
  const cities = ['New York', 'Los Angeles', 'Chicago', 'Houston', 'Phoenix'];

  return (
    <div className="location-filter">
      <select
        id="location-select"
        value={location}
        onChange={(e) => onChange(e.target.value)}
        className="location-filter-select"
      >
        {cities.map((city) => (
          <option key={city} value={city}>
            {city}
          </option>
        ))}
      </select>
    </div>
  );
}

export default LocationFilter;