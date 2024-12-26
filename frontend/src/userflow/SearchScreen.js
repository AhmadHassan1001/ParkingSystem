import React, { useEffect, useState } from 'react';
import Navbar from '../components/Navbar';
import Filters from './Filters';
import './FiltersStyles.css';
import ParkingLotGrid from './ParkingLotGrid';

function SearchScreen() {
  const [filters, setFilters] = useState({
    location: 'New York',
  });
  const [loading, setLoading] = useState(false);
  const [parkingLots, setParkingLots] = useState([]);

  const notifications = [
    { message: 'Parking lot 1 is full' },
    { message: 'Parking lot 2 has a new violation' },
  ];

  useEffect(() => {
    const fetchParkingLots = async () => {
      setLoading(true);
      try {
        const response = await fetch('/parking-lots', {
          headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
          },
        });
        const data = await response.json();
        setParkingLots(data);
      } catch (error) {
        console.error('Error fetching parking lots:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchParkingLots();
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