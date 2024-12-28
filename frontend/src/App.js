import React, { useState } from 'react';
import { Route, BrowserRouter as Router, Routes } from 'react-router-dom';
import './App.css';
import AdminDashboard from './components/AdminDashboard';
import Login from './components/Login';
import ManagerDashboard from './components/ManagerDashboard';
import Signup from './components/Signup';
import SearchScreen from './userflow/SearchScreen';
import ParkingLotDetails from './userflow/ParkingLotDetails';
import ParkingSpotDetails from './userflow/ParkingSpotDetails';
import Navbar from './components/Navbar';
import { UserContext } from './UserContext';
import Copilot from './copilot/Copilot';

function App() {
  const [user, setUser] = useState(null);

  const notifications = [
    { message: 'Parking lot 1 is full' },
    { message: 'Parking lot 2 has a new violation' },
  ];

  return (
    <Router>
      <div className="App">
        <UserContext.Provider value={{ user, setUser }}>
          <Navbar notifications={notifications} />
          <Routes>
            <Route path="/" element={<SearchScreen />} />
            <Route path="/login" element={<Login />} />
            <Route path="/signup" element={<Signup />} />
            <Route path="/search" element={<SearchScreen />} />
            <Route path="/manager-dashboard" element={<ManagerDashboard />} />
            <Route path="/admin-dashboard" element={<AdminDashboard />} />
            <Route path="/parking-lots/:id" element={<ParkingLotDetails />} />
            <Route path="/parking-spots/:id" element={<ParkingSpotDetails />} />
          </Routes>
        </UserContext.Provider>
        <Copilot/>
      </div>
    </Router>
  );
}

export default App;