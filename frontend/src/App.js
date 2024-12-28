import React, { useRef, useState } from 'react';
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
import { PageContext } from './PageContext';

function App() {
  const [user, setUser] = useState(null);
  const divRef = useRef(null); // Create a reference to the div

  return (
    <Router>
      <div className="App" ref={divRef}>
        <UserContext.Provider value={{ user, setUser }}>
          <PageContext.Provider value={{ divRef }}>
            <Navbar />
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
            <Copilot/>
          </PageContext.Provider>

        </UserContext.Provider>
      </div>
    </Router>
  );
}

export default App;