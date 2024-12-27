import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import dummyImg from '../assets/ParkingLot.png';
import './FiltersStyles.css';
import ReserveDialog from './ReserveDialog';

function ParkingSpotDetails() {
  const { id } = useParams();
  const [parkingSpot, setParkingSpot] = useState({
    id: 1,
    type: 'REGULAR',
    status: 'AVAILABLE',
    reservations: [
      {
        id: 1,
        startTime: '2021-08-01T10:00:00',
        endTime: '2021-08-01T12:00:00',
      },
      {
        id: 2,
        startTime: '2021-08-01T14:00:00',
        endTime: '2021-08-01T16:00:00',
      },
    ],
  });

  const [selectedSpot, setSelectedSpot] = useState(null);

  
  useEffect(() => {
    // TODO: Fetch parking spot details
  }, [id]);

  const handleReserve = (spotId) => {
    setSelectedSpot(spotId);
  };

  const handleDialogClose = () => {
    setSelectedSpot(null);
  };

  const handleReserveConfirm = async (spotId, startTime, endTime, price) => {
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
      } else {
        console.error('Reservation failed');
      }
    } catch (error) {
      console.error('Error:', error);
    }
  };

  if (!parkingSpot) {
    return <div>Loading...</div>;
  }

  return (
    <div className="parking-lot-details">
      <div className="content">
        <img src={dummyImg} alt={`Parking Lot ${parkingSpot.id}`} className="image" />
        <div className="content">
          <div className="header">
            <h3 className="title">Parking Lot {parkingSpot.id}</h3>
            <label>{parkingSpot.location.city}, {parkingSpot.location.street}</label>
            <a href={parkingSpot.location.mapLink} target="_blank" rel="noopener noreferrer" className="map-link">View on Map</a>
            <label>Capacity: {parkingSpot.capacity}</label>
            <label>Base Price: ${parkingSpot.basicPrice}</label>
          </div>
        </div>
      </div>
      <div className="spots">
        <h4 className="title">Parking Spots:</h4>
        <ul>
          {parkingSpot.parkingSpots.map((spot) => (
            <li key={spot.id} className={`spot ${spot.status.toLowerCase()}`}>
              <span>Spot {spot.id} - {spot.type} - {spot.status}</span>
              {spot.status === 'AVAILABLE' && (
                <button onClick={() => handleReserve(spot.id)} className="reserve-button">Reserve</button>
              )}
            </li>
          ))}
        </ul>
      </div>
      {selectedSpot && (
        <ReserveDialog
          spotId={selectedSpot}
          basicPrice={parkingSpot.basicPrice}
          onClose={handleDialogClose}
          onReserve={handleReserveConfirm}
        />
      )}
    </div>
  );
}

export default ParkingSpotDetails;