import React, { useContext, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import dummyImg from '../assets/ParkingLot.png';
import './ParkingSpotDetials.css';
import ReserveDialog from './ReserveDialog';
import { parkingLotDetails, parkingSpotDetails, reservationPaid } from '../api';
import { UserContext } from '../UserContext';

function ParkingSpotDetails() {
  const { id } = useParams();
  const [parkingSpot, setParkingSpot] = useState({
    id: 1,
    type: 'REGULAR',
    status: 'OCCUPIED',
    reservationsInfo: [
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

  const [parkingLot, setParkingLot] = useState(null);
  
  const {user, setUser} = useContext(UserContext);

  
  useEffect(() => {
    parkingSpotDetails(id).then((data) => {
      setParkingSpot(data);
      parkingLotDetails(data.parkingLotId).then((data) => {
        setParkingLot(data);
      });
    });

  }, [id]);

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

  const formateDate = (date) => {
    return new Date(date).toLocaleString();
  }

  const handlePayment = (reservationId) => {
    reservationPaid(reservationId).then((data) => {

      parkingSpotDetails(id).then((data) => {
        setParkingSpot(data);
        parkingLotDetails(data.parkingLotId).then((data) => {
          setParkingLot(data);
        });
      });
      alert(`Payment for reservation ${reservationId} was successful`);
    }).catch((error) => {
      console.error('Error:', error);
    });
  }

  return (
    <div className="parking-lot-details">
      <div className="content">
        <img src={dummyImg} alt={`Parking Lot ${parkingSpot.id}`} className="image" />
        <div className="content">
          <div className="header">
            <h3 className="title">Parking Lot {parkingSpot.id}</h3>
            <span className="info"> 
              <label className='info-label'>Type:</label>
              <label className='type'>{parkingSpot.type}</label>
            </span>

            <span className="info"> 
              <label className='info-label'>Status:</label>
              <label className={`status ${parkingSpot.status.toLowerCase()}`}>{parkingSpot.status}</label>
            </span>
          </div>
        </div>
      </div>
      <div className="spots">
        {parkingLot?.managerId == user?.id && (<>
        <h4 className="title">Reservations:</h4>
        <ul>
          {parkingSpot.reservationsInfo?.map((reservation) => (
           !reservation.paid && ( <li key={reservation.id} className={`reservation`}>
              <label>{formateDate(reservation.startTime)} - {formateDate(reservation.endTime)}</label>
              <button onClick={()=>handlePayment(reservation.id)}>Paid</button>
            </li>)
          ))}
        </ul>
        </>)
        }
      </div>
    </div>
  );
}

export default ParkingSpotDetails;