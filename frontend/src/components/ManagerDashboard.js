import React, { useEffect, useState } from 'react';
import './AdminDashboard.css';
import './ManagerDashboard.css';

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

  const viewReports = async () => {
    try {
      const response = await fetch('http://localhost:8080/reports/manager-dashboard', {
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('token')}`,
        },
      });
    } catch (error) {
        console.error('Error viewing reports:', error);
    }
  };

  return (
    <div className="dashboard">
      <div>
        <h2>Parking Lot Dashboard</h2>
        <button className="dashboard-button" onClick={viewReports}>Generate Reports</button>
      </div>
    </div>
  );
}

export default ManagerDashboard;