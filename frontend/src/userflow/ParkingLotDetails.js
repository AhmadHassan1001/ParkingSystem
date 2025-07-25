import React, { useContext, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import dummyImg from '../assets/ParkingLot.png';
import './FiltersStyles.css';
import ReserveDialog from './ReserveDialog';
import { parkingLotDetails, reserveSpot } from '../api';
import { useNavigate } from 'react-router-dom';
import { UserContext } from '../UserContext';

function ParkingLotDetails() {
  const { id } = useParams();
  const [parkingLot, setParkingLot] = useState(null);
  const [selectedSpot, setSelectedSpot] = useState(null);
  const navigate = useNavigate();
  const { user, setUser } = useContext(UserContext);

  
  useEffect(() => {
    parkingLotDetails(id).then((data) => {
      setParkingLot(data);
    });

    // add set interval to update spot data each 1sec
    setInterval(() => {
      parkingLotDetails(id).then((data) => {
        setParkingLot(data);
      }).catch((error) => {
        console.error('Error:', error);
      });
    }, 1000);
  }, [id]);

  const handleReserve = (spotId) => {
    setSelectedSpot(spotId);
  };

  const handleDialogClose = () => {
    setSelectedSpot(null);
  };

  const handleReserveConfirm = async (startTime, endTime) => {
    reserveSpot(selectedSpot, startTime, endTime).then((data) => {
      alert(`Reserved spot ${selectedSpot} from ${startTime} to ${endTime} for $${data.cost.toFixed(2)}`);
      setSelectedSpot(null);
      // refresh page
      parkingLotDetails(id).then((data) => {
        setParkingLot(data);
      });

    });
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
              {spot.status === 'AVAILABLE' &&
                parkingLot.managerId !== user.id && (
                <button onClick={() => handleReserve(spot.id)} className="reserve-button">Reserve</button>
              )}
              <button className="spot-details" onClick={() => navigate(`/parking-spots/${spot.id}`)}>Details</button>
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