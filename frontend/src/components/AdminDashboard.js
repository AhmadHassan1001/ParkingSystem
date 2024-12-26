import React, { useEffect, useState } from 'react';
import './AdminDashboard.css';
import Navbar from './Navbar';

function AdminDashboard() {
  const [notifications, setNotifications] = useState([]);
  const [topParkingLots, setTopParkingLots] = useState([]);
  const [topDrivers, setTopDrivers] = useState([]);

  useEffect(() => {
    const fetchAdminDashboardData = async () => {
      try {
        const response = await fetch('/admin-dashboard', {
          headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
          },
        });
        const data = await response.json();
        setTopParkingLots(data.topParkingLots);
        setTopDrivers(data.topDrivers);
      } catch (error) {
        console.error('Error fetching admin dashboard data:', error);
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

    fetchAdminDashboardData();
    fetchNotifications();
  }, []);

  return (
    <div className="dashboard">
      <Navbar notifications={notifications} />
      <div>
        <h2>Admin Dashboard</h2>
        <div className="dashboard-section">
          <h3>Top Parking Lots</h3>
          <div className="dashboard-grid">
            {topParkingLots.map((lot) => (
              <div key={lot.id} className="dashboard-card">
                <h4>{lot.name}</h4>
                <p>Occupancy Rate: {lot.occupancyRate}%</p>
                <p>Revenue: ${lot.revenue}</p>
                <p>Violations: {lot.violations}</p>
              </div>
            ))}
          </div>
        </div>
        <div className="dashboard-section">
          <h3>Top Drivers</h3>
          <div className="dashboard-grid">
            {topDrivers.map((driver) => (
              <div key={driver.id} className="dashboard-card">
                <h4>{driver.name}</h4>
                <p>Total Bookings: {driver.totalBookings}</p>
                <p>Total Spent: ${driver.totalSpent}</p>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}

export default AdminDashboard;