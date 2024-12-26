import React from 'react';
import './AdminDashboard.css';
import Navbar from './Navbar';

function AdminDashboard() {
  const notifications = [
    { message: 'New user signed up' },
    { message: 'Parking lot 3 has a new violation' },
  ];

  const topParkingLots = [
    {
      id: 1,
      name: 'Parking Lot 1',
      occupancyRate: 95,
      revenue: 10000,
      violations: 1,
    },
    {
      id: 2,
      name: 'Parking Lot 2',
      occupancyRate: 90,
      revenue: 8000,
      violations: 2,
    },
  ];

  const topDrivers = [
    {
      id: 1,
      name: 'John Doe',
      totalBookings: 50,
      totalSpent: 500,
    },
    {
      id: 2,
      name: 'Jane Smith',
      totalBookings: 45,
      totalSpent: 450,
    },
  ];

  return (
    <div  className="dashboard">
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