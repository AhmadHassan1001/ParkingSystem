import React, { useEffect, useState } from 'react';
import './FiltersStyles.css';
import { locationList } from '../api';

function LocationFilter({ location, onChange }) {
  const [cities, setCities] = useState([]);

  useEffect(() => {
    locationList().then((data) => {
      setCities(data.map((location) => location.city));
    });
  }, []);

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