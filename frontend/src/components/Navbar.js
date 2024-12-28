import React, { useState, useEffect, useContext } from 'react';
import './Navbar.css';
import { useNavigate } from 'react-router-dom';
import ExitToAppIcon from '@mui/icons-material/ExitToApp';
import DashboardIcon from '@mui/icons-material/Dashboard';
import { info } from '../api';
import { UserContext } from '../UserContext';
import {EventSource} from 'eventsource'

function Navbar() {
  const [showNotifications, setShowNotifications] = useState(false);
  const [dashboard, setDashboard] = useState(null);
  const [notifications, setNotifications] = useState([]);
  const [newNotification, setNewNotification] = useState(false);
  const { user, setUser } = useContext(UserContext);
  let maxNotificationId=0;
  let eventSource;

  const navigate = useNavigate();
  
  const toggleNotifications = () => {
    if(!showNotifications)
      setNewNotification(false);
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

  const establishSSE = () => {
    const token = localStorage.getItem('token');
    eventSource = new EventSource(`http://localhost:8080/sse`, {
      fetch: (input, init) =>
        fetch(input, {
          ...init,
          headers: {
            ...init.headers,
            Authorization: `Bearer ${token}`,
          },
        }),
    })

    eventSource.onmessage = (event) => {
      const notifications = JSON.parse(event.data);
      // get maximum id
      let newNotificationId = Math.max.apply(Math, notifications.map(function(o) { return o.id; }));
      if(newNotificationId > maxNotificationId){
        maxNotificationId = newNotificationId;
        setNewNotification(true);
      }
      setNotifications(notifications.map((notification) => (notification.bodyText)));
    };

    eventSource.onerror = (error) => {
      console.error('EventSource failed:', error);
      // eventSource.close();
    }

  }

  useEffect(() => {
    if(localStorage.getItem('token')) {
      info().then((data) => {
        setUser(data);
        
      });
    }

    establishSSE();


    return () => {  
      eventSource.close();
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
          {newNotification && <span className="notification-dot"></span>}
          {showNotifications && (
            <div className="notification-dropdown">
              {notifications.length > 0 ? (
                notifications.map((notification, index) => (
                  <div key={index} className="notification-item">
                    {notification}
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