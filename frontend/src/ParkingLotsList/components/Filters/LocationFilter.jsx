import React from 'react';
import { FormControl, InputLabel, Select, MenuItem } from '@mui/material';
import styles from './LocationFilter.module.css'


function LocationFilter({ location, onChange }) {
  const cities = ['New York', 'Los Angeles', 'Chicago', 'Houston', 'Phoenix'];

  return (
    <FormControl fullWidth>
      <label id="location-select-label" className={styles.location_filter_label}>City:</label>
      <Select
        labelId="location-select-label"
        value={location}
        onChange={(e) => onChange(e.target.value)}
        label="City"
        className={styles.location_filter}
      >
        {cities.map((city) => (
          <MenuItem key={city} value={city}>
            {city}
          </MenuItem>
        ))}
      </Select>
    </FormControl>
  );
}

export default LocationFilter;