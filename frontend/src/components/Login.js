import React, { useContext, useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Login.css';
import { info, login } from '../api';
import { UserContext } from '../UserContext';

function Login() {
  const [name, setName] = useState('');
  const [password, setPassword] = useState('');
  const { user, setUser } = useContext(UserContext);

  const navigate = useNavigate();

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      login(name, password).then((response) => {
        info().then((data) => {
          console.log(data);
          setUser(data);


          const role = data.role;
          // Redirect based on user role
          if (role === 'DRIVER') {
            navigate('/search');
          } else if (role === 'LOT_MANAGER') {
            navigate('/manager-dashboard');
          } else if (role === 'ADMIN') {
            navigate('/admin-dashboard');
          }

        });
      }).catch((error) => {
        console.error('Error:', error);
      });

    } catch (error) {
      console.error('Error:', error);
    }
  };

  useEffect(() => {
    const token = window.localStorage.getItem('token');
    if (token) {
      info().then((data) => {
        console.log(data);
        const role = data.role;
        // Redirect based on user role
        if (role === 'DRIVER') {
          navigate('/search');
        } else if (role === 'LOT_MANAGER') {
          navigate('/manager-dashboard');
        } else if (role === 'ADMIN') {
          navigate('/admin-dashboard');
        }

      });
    }
  }, []);

  return (
    <div className="login-container">
      <div className="login-box">
        <h1>Smart City Parking</h1>
        <h2>Login</h2>
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Name:</label>
            <input
              type="text"
              name="name"
              value={name}
              onChange={(e) => setName(e.target.value)}
            />
          </div>
          <div className="form-group">
            <label>Password:</label>
            <input
              type="password"
              name="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </div>
          <button type="submit" className="login-button">Login</button>
        </form>
        <button className="signup-button" onClick={() => navigate('/signup')}>Create Account</button>
      </div>
    </div>
  );
}

export default Login;