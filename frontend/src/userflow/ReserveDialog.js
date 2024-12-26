import React, { useState } from 'react';
import './FiltersStyles.css';

function ReserveDialog({ spotId, basicPrice, onClose, onReserve }) {
  const [startTime, setStartTime] = useState('');
  const [endTime, setEndTime] = useState('');
  const [price, setPrice] = useState(0);
  const [availability, setAvailability] = useState(null);

  const checkAvailability = async () => {
    try {
      const response = await fetch(`/reserve/${spotId}?startTime=${startTime}&endTime=${endTime}`, {
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('token')}`,
        },
      });
      const data = await response.json();
      setAvailability(data.available);
      if (data.available) {
        setPrice(data.price);
      } else {
        setPrice(0);
      }
    } catch (error) {
      console.error('Error checking availability:', error);
    }
  };

  const handleReserve = async () => {
    if (availability) {
      try {
        const response = await fetch(`/reserve/${spotId}`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
          },
          body: JSON.stringify({ startTime, endTime }),
        });

        if (response.ok) {
          alert(`Reserved spot ${spotId} from ${startTime} to ${endTime} for $${price.toFixed(2)}`);
          onReserve(spotId, startTime, endTime, price);
          onClose();
        } else {
          console.error('Reservation failed');
        }
      } catch (error) {
        console.error('Error:', error);
      }
    } else {
      alert('Selected time is not available for reservation.');
    }
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
            onChange={(e) => setStartTime(e.target.value)}
          />
        </div>
        <div className="form-group">
          <label>End Time:</label>
          <input
            type="datetime-local"
            value={endTime}
            onChange={(e) => setEndTime(e.target.value)}
          />
        </div>
        <div className="form-group">
          <label>Price: ${price.toFixed(2)}</label>
        </div>
        <button onClick={checkAvailability} className="calculate-button">Check Availability</button>
        <button onClick={handleReserve} className="d-reserve-button">Reserve</button>
        <button onClick={onClose} className="close-button">Close</button>
      </div>
    </div>
  );
}

export default ReserveDialog;