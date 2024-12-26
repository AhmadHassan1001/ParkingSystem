import React from 'react';
import './FiltersStyles.css';
import ParkingLotCard from './ParkingLotCard';

function ParkingLotGrid({ parkingLots, loading }) {
  if (loading) {
    return (
      <div className="parking-lot-grid">
        {[...Array(6)].map((_, index) => (
          <div key={index} className="skeleton">
            <div className="skeleton-image"></div>
            <div className="skeleton-content">
              <div className="skeleton-title"></div>
              <div className="skeleton-text"></div>
            </div>
          </div>
        ))}
      </div>
    );
  }

  return (
    <div className="parking-lot-grid">
      {parkingLots.map((parkingLot) => (
        <ParkingLotCard key={parkingLot.id} parkingLot={parkingLot} />
      ))}
    </div>
  );
}

export default ParkingLotGrid;