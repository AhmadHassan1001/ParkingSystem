import React, { useEffect, useState } from 'react';
import './FiltersStyles.css';

function LocationFilter({ location, onChange }) {
  const [cities, setCities] = useState([]);

  useEffect(() => {
    const fetchLocations = async () => {
      try {
        const response = await fetch('/locations', {
          headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
          },
        });
        const data = await response.json();
        setCities(data);
      } catch (error) {
        console.error('Error fetching locations:', error);
      }
    };

    fetchLocations();
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