import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import dummyImg from '../assets/ParkingLot.png';
import './FiltersStyles.css';
import ReserveDialog from './ReserveDialog';
import { parkingLotDetails } from '../api';

function ParkingLotDetails() {
  const { id } = useParams();
  const [parkingLot, setParkingLot] = useState({
    location: {
      city: 'City',
      street: 'Street',
      mapLink: 'https://www.google.com/maps',
    },
    parkingSpots: [
      {
        id: 1,
        type: 'REGULAR',
        status: 'AVAILABLE',
      },
      {
        id: 2,
        type: 'DISABLED',
        status: 'RESERVED',
      },
      {
        id: 3,
        type: 'EV',
        status: 'OCCUPIED',
      },
    ],
  });
  const [selectedSpot, setSelectedSpot] = useState(null);

  
  useEffect(() => {
    parkingLotDetails(id).then((data) => {
      setParkingLot(data);
    });
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

  if (!parkingLot) {
    return <div>Loading...</div>;
  }

  return (
    <div className="parking-lot-details">
      <div className="content">
        <img src={dummyImg} alt={`Parking Lot ${parkingLot.id}`} className="image" />
        <div className="content">
          <div className="header">
            <h3 className="title">Parking Lot {parkingLot.id}</h3>
            <label>{parkingLot.location.city}, {parkingLot.location.street}</label>
            <a href={parkingLot.location.mapLink} target="_blank" rel="noopener noreferrer" className="map-link">View on Map</a>
            <label>Capacity: {parkingLot.capacity}</label>
            <label>Base Price: ${parkingLot.basicPrice}</label>
          </div>
        </div>
      </div>
      <div className="spots">
        <h4 className="title">Parking Spots:</h4>
        <ul>
          {parkingLot.parkingSpots.map((spot) => (
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
          basicPrice={parkingLot.basicPrice}
          onClose={handleDialogClose}
          onReserve={handleReserveConfirm}
        />
      )}
    </div>
  );
}

export default ParkingLotDetails;