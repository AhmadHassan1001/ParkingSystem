import React, { useState } from 'react';
import './FiltersStyles.css';
import { calculatePrice, reserveSpot } from '../api';

function ReserveDialog({ spotId, basicPrice, onClose, onReserve }) {
  const [startTime, setStartTime] = useState('');
  const [endTime, setEndTime] = useState('');
  const [price, setPrice] = useState(0);
  const [availability, setAvailability] = useState(null);

  const getPrice = async () => {
    calculatePrice(spotId, startTime, endTime).then((data) => {
      setPrice(data);
      setAvailability(true);
    });
  };

  const handleReserve = async () => {
    onReserve(startTime, endTime);
  };

  return (
    <div className="reserve-dialog">
      <div className="dialog-content">
        <h2>Reserve Spot {spotId}</h2>
        <div className="form-group">
          <label>Start Time:</label>
          <input
            type="datetime-local"
            value={startTime}
            onChange={(e) => setStartTime((e.target.value))}
          />
        </div>
        <div className="form-group">
          <label>End Time:</label>
          <input
            type="datetime-local"
            value={endTime}
            onChange={(e) => setEndTime((e.target.value))}
          />
        </div>
        <div className="form-group">
          <label>Price: ${price.toFixed(2)}</label>
        </div>
        <button onClick={getPrice} className="calculate-button">Calculate Price</button>
        <button onClick={handleReserve} className="d-reserve-button">Reserve</button>
        <button onClick={onClose} className="close-button">Close</button>
      </div>
    </div>
  );
}

export default ReserveDialog;