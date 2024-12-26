import React, { useState } from 'react';
import { useParams } from 'react-router-dom';
import dummyImg from '../assets/ParkingLot.png';
import ReserveDialog from '../userflow/ReserveDialog';
import './FiltersStyles.css';

const testParkingLot = {
  id: 1,
  location: { city: 'New York', street: '5th Avenue', mapLink: 'https://maps.app.goo.gl/vo6MmxpEBaQT9bMC7' },
  capacity: 3,
  basicPrice: 20,
  parkingSpots: [
    { id: 1, type: 'REGULAR', status: 'AVAILABLE' },
    { id: 2, type: 'DISABLED', status: 'OCCUPIED' },
    { id: 3, type: 'EV', status: 'RESERVED' },
    { id: 4, type: 'REGULAR', status: 'AVAILABLE' },
    { id: 5, type: 'DISABLED', status: 'OCCUPIED' },
    { id: 6, type: 'EV', status: 'RESERVED' },
    { id: 7, type: 'REGULAR', status: 'AVAILABLE' },
    { id: 8, type: 'DISABLED', status: 'OCCUPIED' },
    { id: 9, type: 'EV', status: 'RESERVED' },
  ],
};

function ParkingLotDetails() {
  const { id } = useParams();
  const [parkingLot] = useState(testParkingLot); // Replace with actual data fetching logic
  const [selectedSpot, setSelectedSpot] = useState(null);

  const handleReserve = (spotId) => {
    setSelectedSpot(spotId);
  };

  const handleDialogClose = () => {
    setSelectedSpot(null);
  };

  const handleReserveConfirm = (spotId, startTime, endTime, price) => {
    // Implement reservation logic here
    alert(`Reserved spot ${spotId} from ${startTime} to ${endTime} for $${price.toFixed(2)}`);
  };

  return (
    <div className="parking-lot-details">
      <div class="content">
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
              {/* {spot.status === 'AVAILABLE' && ( */}
              {spot.status != null && (
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