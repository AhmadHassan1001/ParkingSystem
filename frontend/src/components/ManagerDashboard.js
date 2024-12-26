import React from 'react';
import './ManagerDashboard.css';
import Navbar from './Navbar';

function ManagerDashboard() {
  const notifications = [
    { message: 'Parking lot 1 is full' },
    { message: 'Parking lot 2 has a new violation' },
  ];

  const parkingLots = [
    {
      id: 1,
      name: 'Parking Lot 1',
      occupancyRate: 90,
      revenue: 5000,
      violations: 2,
    }
  ];

  return (
    <div className="dashboard">
      <Navbar notifications={notifications} />
      <div >
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