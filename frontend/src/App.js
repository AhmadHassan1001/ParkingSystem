import React from 'react';
import { Route, BrowserRouter as Router, Routes } from 'react-router-dom';
import './App.css';
import AdminDashboard from './components/AdminDashboard';
import Login from './components/Login';
import ManagerDashboard from './components/ManagerDashboard';
import Signup from './components/Signup';
import SearchScreen from './userflow/SearchScreen';
import ParkingLotDetails from './userflow/ParkingLotDetails';
import ParkingSpotDetails from './userflow/ParkingSpotDetails';

function App() {
  return (
    <Router>
      <div className="App">
        <Routes>
          <Route path="/" element={<Login />} />
          <Route path="/signup" element={<Signup />} />
          <Route path="/search" element={<SearchScreen />} />
          <Route path="/manager-dashboard" element={<ManagerDashboard />} />
          <Route path="/admin-dashboard" element={<AdminDashboard />} />
          <Route path="/parking-lot/:id" element={<ParkingLotDetails />} />
          <Route path="/parking-spot/:id" element={<ParkingSpotDetails />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;