import React, { useState, useEffect, useContext } from 'react';
import './Navbar.css';
import { useNavigate } from 'react-router-dom';
import ExitToAppIcon from '@mui/icons-material/ExitToApp';
import DashboardIcon from '@mui/icons-material/Dashboard';
import { info } from '../api';
import { UserContext } from '../UserContext';

function Navbar({ notifications }) {
  const [showNotifications, setShowNotifications] = useState(false);
  const [dashboard, setDashboard] = useState(null);
  const { user, setUser } = useContext(UserContext);

  const navigate = useNavigate();
  
  const toggleNotifications = () => {
    setShowNotifications(!showNotifications);
  };

  const logOut = () => {
    window.localStorage.removeItem('token');
    navigate('/login');
  }

  const toDashboard = () => {
    let dashboard;
    if(user.role === 'LOT_MANAGER') {
      dashboard = ('/manager-dashboard');
    } else if(user.role === 'ADMIN') {
      dashboard = ('/admin-dashboard');
    } else {
      dashboard = (null);
    }
    navigate(dashboard)
  }

  const showDashboard = ()=>{
    return user?.role == "LOT_MANAGER" || user?.role == "ADMIN";
  }

  useEffect(() => {
    if(localStorage.getItem('token')) {
      info().then((data) => {
        setUser(data);
        
      });
    }
  }, []);

  return (
    <div className="navbar">
      <h1 onClick={()=>{navigate("/")}}>
        Smart City Parking
      </h1>
      <div className='actions'>
        <div className="notifications">
          <span className="notification-icon" onClick={toggleNotifications}>🔔</span>
          {showNotifications && (
            <div className="notification-dropdown">
              {notifications.length > 0 ? (
                notifications.map((notification, index) => (
                  <div key={index} className="notification-item">
                    {notification.message}
                  </div>
                ))
              ) : (
                <div className="notification-item">No notifications</div>
              )}
            </div>
          )}
        </div>
        {showDashboard() && <div className="dashboard" onClick={toDashboard}>
          <DashboardIcon fontSize="large"/>
        </div>}
        <div className="logout" onClick={logOut}>
          <ExitToAppIcon fontSize="large"/>
        </div>
      </div>
    </div>
  );
}

export default Navbar;