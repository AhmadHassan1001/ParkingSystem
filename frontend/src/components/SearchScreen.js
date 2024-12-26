import React, { useState } from 'react';
import Filters from './Filters';
import './FiltersStyles.css';
import ParkingLotGrid from './ParkingLotGrid';

function SearchScreen() {
  const [filters, setFilters] = useState({
    location: 'New York',
  });
  const [loading, setLoading] = useState(false);

  const parkingLots = [
    {
      id: 1,
      location: { city: 'New York', street: '5th Avenue' },
      capacity: 100,
      price: 20,
    },
    {
      id: 2,
      location: { city: 'New York', street: 'Madison Avenue' },
      capacity: 150,
      price: 25,
    },
    {
      id: 3,
      location: { city: 'New York', street: 'Broadway' },
      capacity: 200,
      price: 30,
    },
    {
      id: 4,
      location: { city: 'Los Angeles', street: 'Sunset Boulevard' },
      capacity: 120,
      price: 22,
    },
  ];

  const filteredParkingLots = parkingLots.filter(
    (lot) => lot.location.city === filters.location
  );

  return (
    <div className="search-screen">
      <Filters filters={filters} setFilters={setFilters} />
      <ParkingLotGrid parkingLots={filteredParkingLots} loading={loading} />
    </div>
  );
}

export default SearchScreen;