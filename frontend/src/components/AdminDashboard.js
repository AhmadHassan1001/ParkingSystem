import React from 'react';
import './AdminDashboard.css';

function AdminDashboard() {
  
  const viewReports = async () => {
    try {
        await fetch('http://localhost:8080/reports/admin-dashboard');
    } catch (error) {
        console.error('Error viewing reports:', error);
    }
  };

  return (
    <div className="dashboard">
      <div>
        <h2>Admin Dashboard:</h2>
        <button className="dashboard-button" onClick={viewReports}>Generate Reports</button>
      </div>
    </div>
  );
}

export default AdminDashboard;