import React from 'react';
import { useNavigate } from 'react-router-dom';
import Rating from '@mui/material/Rating';
import styles from './ParkingLotCard.module.css';
import dummyImg from '../../../assets/ParkingLot.png';

const ParkingLotCard = ({ parkingLot }) => {
  const navigate = useNavigate();

  const handleCardClick = () => {
    navigate(`/parking-lot/${parkingLot.id}`);
  };

  return (
    <div className={styles.card} onClick={handleCardClick}>
      <img 
        src={dummyImg} 
        alt={parkingLot.name} 
        className={styles.image}
      />
      <div className={styles.content}>
        <div className={styles.header}>
          <h3 className={styles.title}>{parkingLot.name}</h3>
          <p className={styles.location}>{parkingLot.location}</p>
        </div>
        <div className={styles.footer}>
          <Rating value={parkingLot.rating} precision={0.1} readOnly />
          <span className={styles.types}>
            Available Types: {parkingLot.availableTypes.join(', ')}
          </span>
        </div>
      </div>
    </div>
  );
};

export default ParkingLotCard;