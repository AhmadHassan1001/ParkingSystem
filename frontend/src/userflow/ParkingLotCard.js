import React from 'react';
import { useNavigate } from 'react-router-dom';
import dummyImg from '../assets/ParkingLot.png';
import './FiltersStyles.css';

function ParkingLotCard({ parkingLot }) {
  const navigate = useNavigate();

  const handleCardClick = () => {
    navigate(`/parking-lots/${parkingLot.id}`);
  };

  return (
    <div className="parking-lot-card" onClick={handleCardClick}>
      <img src={dummyImg} alt={`Parking Lot ${parkingLot.id}`} className="image" />
      <div className="content">
        <div className="header">
          <h3 className="title">Parking Lot {parkingLot.id}</h3>
          <p className="location">{parkingLot.location.city}, {parkingLot.location.street}</p>
        </div>
        <div className="footer">
          <span className="price">Price: ${parkingLot.basicPrice}</span>
        </div>
      </div>
    </div>
  );
}

export default ParkingLotCard;