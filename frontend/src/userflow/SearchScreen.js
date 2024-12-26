import React, { useState } from 'react';
import Navbar from '../components/Navbar';
import Filters from './Filters';
import './FiltersStyles.css';
import ParkingLotGrid from './ParkingLotGrid';

function SearchScreen() {
  const [filters, setFilters] = useState({
    location: 'New York',
  });
  const [loading, setLoading] = useState(false);

  const notifications = [
    { message: 'Parking lot 1 is full' },
    { message: 'Parking lot 2 has a new violation' },
  ];

  const parkingLots = [
    {
      id: 1,
      location: { city: 'New York', street: '5th Avenue' },
      capacity: 100,
      basicPrice: 20,
    },
    {
      id: 2,
      location: { city: 'New York', street: 'Madison Avenue' },
      capacity: 150,
      basicPrice: 25,
    },
    {
      id: 3,
      location: { city: 'New York', street: 'Broadway' },
      capacity: 200,
      basicPrice: 30,
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
      <Navbar notifications={notifications} />
      <Filters filters={filters} setFilters={setFilters} />
      <ParkingLotGrid parkingLots={filteredParkingLots} loading={loading} />
    </div>
  );
}

export default SearchScreen;