import React, { useEffect, useState } from 'react';
import Navbar from '../components/Navbar';
import Filters from './Filters';
import './FiltersStyles.css';
import ParkingLotGrid from './ParkingLotGrid';
import { parkingLotList } from '../api';

function SearchScreen() {
  const [filters, setFilters] = useState({
    location: 'New York',
  });
  const [loading, setLoading] = useState(true);
  const [parkingLots, setParkingLots] = useState([
    {
      id: 1,
      location: {
        city: 'New York',
        street: 'Street 1',
        mapLink: 'https://www.google.com/maps',
      },
      basicPrice: 10,
      slots: {
        REGULAR: 10,
        DISABLED: 2,
        EV: 1,
      },
    },
    {
      id: 2,
      location: {
        city: 'New York',
        street: 'Street 2',
        mapLink: 'https://www.google.com/maps',
      },
      basicPrice: 15,
      slots: {
        REGULAR: 5,
        DISABLED: 1,
        EV: 0,
      },
    },
    {
      id: 3,
      location: {
        city: 'Los Angeles',
        street: 'Street 3',
        mapLink: 'https://www.google.com/maps',
      },
      basicPrice: 20,
      slots: {
        REGULAR: 8,
        DISABLED: 0,
        EV: 3,
      },
    },
  ]);

  const notifications = [
    { message: 'Parking lot 1 is full' },
    { message: 'Parking lot 2 has a new violation' },
  ];

  useEffect(() => {
    parkingLotList().then((data) => {
      setParkingLots(data);
      setLoading(false);
    });

  }, []);

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