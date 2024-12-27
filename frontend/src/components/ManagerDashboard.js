import React, { useEffect, useState } from 'react';
import './ManagerDashboard.css';
import Navbar from './Navbar';

function ManagerDashboard() {
  const [notifications, setNotifications] = useState([]);
  const [parkingLots, setParkingLots] = useState([]);

  useEffect(() => {
    const fetchManagerDashboardData = async () => {
      try {
        const response = await fetch('/dashboard', {
          headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
          },
        });
        const data = await response.json();
        setParkingLots(data.parkingLots);
      } catch (error) {
        console.error('Error fetching manager dashboard data:', error);
      }
    };

    const fetchNotifications = async () => {
      try {
        const response = await fetch('/notifications', {
          headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
          },
        });
        const data = await response.json();
        setNotifications(data);
      } catch (error) {
        console.error('Error fetching notifications:', error);
      }
    };

    fetchManagerDashboardData();
    fetchNotifications();
  }, []);

  return (
    <div className="dashboard">
      <div>
        <h2>Parking Lot Dashboard</h2>
        <div className="dashboard-grid">
          {parkingLots.map((lot) => (
            <div key={lot.id} className="dashboard-card">
              <h3>{lot.name}</h3>
              <p>Occupancy Rate: {lot.occupancyRate}%</p>
              <p>Revenue: ${lot.revenue}</p>
              <p>Violations: {lot.violations}</p>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

export default ManagerDashboard;