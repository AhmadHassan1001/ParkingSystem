import React, { useEffect, useState } from 'react';
import ParkingLotCard from './ParkingLotCard';
import styles from './ParkingLotGrid.module.css';

const ParkingLotGrid = () => {
  const [parkingLots, setParkingLots] = useState([
    {
      id: 1,
      name: 'Parking Lot 1',
      rating: 4.5,
      availableTypes: ['Car', 'Bike'],
      location: 'New York, USA',
    },
    {
      id: 2,
      name: 'Parking Lot 2',
      rating: 4.0,
      availableTypes: ['Car'],
      location: 'California, USA',
    },
    {
      id: 3,
      name: 'Parking Lot 3',
      rating: 3.5,
      availableTypes: ['Bike'],
      location: 'Texas, USA',
    },
    {
      id: 4,
      name: 'Parking Lot 4',
      rating: 4.8,
      availableTypes: ['Car', 'Bike'],
      location: 'Florida, USA',
    },
    {
      id: 5,
      name: 'Parking Lot 5',
      rating: 4.2,
      availableTypes: ['Car'],
      location: 'Washington, USA',
    },
    {
      id: 6,
      name: 'Parking Lot 6',
      rating: 3.9,
      availableTypes: ['Bike'],
      location: 'Illinois, USA',
    },
  ]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    // Fetch parking lots data here and update state
  }, []);

  if (loading) {
    return (
      <div className={styles.grid}>
        {[...Array(6)].map((_, index) => (
          <div key={index} className={styles.skeleton}>
            <div className={styles.skeletonImage}></div>
            <div className={styles.skeletonContent}>
              <div className={styles.skeletonTitle}></div>
              <div className={styles.skeletonText}></div>
            </div>
          </div>
        ))}
      </div>
    );
  }

  return (
    <div className={styles.grid}>
      {parkingLots.map((parkingLot) => (
        <ParkingLotCard key={parkingLot.id} parkingLot={parkingLot} />
      ))}
    </div>
  );
};

export default ParkingLotGrid;