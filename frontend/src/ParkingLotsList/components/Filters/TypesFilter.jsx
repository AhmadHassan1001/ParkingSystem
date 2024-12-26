import React from 'react';

const types = ['Regular', 'Disabled', 'EV Charging'];

const TypesFilter = ({ selected, onChange }) => {
  const handleCheckboxChange = (type) => {
    // Toggle the selected state for the type
    const newSelected = selected.includes(type)
      ? selected.filter((item) => item !== type) // Remove the type
      : [...selected, type]; // Add the type
    onChange(newSelected);
    console.log(selected);
  };

  return (
    <div className="mb-6">
      <h3 className="text-white mb-2">Available Spot Types:</h3>
      <div className="space-y-2">
        {types.map((type) => (
          <label key={type} className="flex items-center text-white">
            <input
              type="checkbox"
              className="mr-2"
              checked={selected.includes(type)}
              onChange={() => handleCheckboxChange(type)}
            />
            {type}
          </label>
        ))}
      </div>
    </div>
  );
};

export default TypesFilter;
