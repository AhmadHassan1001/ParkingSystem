import React, { useState } from 'react';
import './Navbar.css';

function Navbar({ notifications }) {
  const [showNotifications, setShowNotifications] = useState(false);

  const toggleNotifications = () => {
    setShowNotifications(!showNotifications);
  };

  return (
    <div className="navbar">
      <h1>Smart City Parking</h1>
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
    </div>
  );
}

export default Navbar;