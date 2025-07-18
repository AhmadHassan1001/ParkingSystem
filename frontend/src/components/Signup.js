import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Signup.css';
import { info, signupDriver, signupParkingLot } from '../api';

function Signup() {
  const [name, setName] = useState('');
  const [password, setPassword] = useState('');
  const [phone, setPhone] = useState('');
  const [licensePlateNumber, setLicensePlateNumber] = useState('');
  const [paymentMethod, setPaymentMethod] = useState('CASH');
  const [city, setCity] = useState('');
  const [street, setStreet] = useState('');
  const [locationLink, setLocationLink] = useState('');
  const [price, setPrice] = useState('');
  const [regularSlots, setRegularSlots] = useState('');
  const [disabledSlots, setDisabledSlots] = useState('');
  const [evSlots, setEvSlots] = useState('');
  const [role, setRole] = useState('DRIVER');
  const navigate = useNavigate();

  const handleSubmit = async (event) => {
    event.preventDefault();


    const userData = {
      name,
      password,
      phone,
      role,
      ...(role === 'DRIVER' && { licensePlateNumber, paymentMethod }),
      ...(role === 'MANAGEMENT' && { parkingLotName: name, city, street, locationLink, capacity: regularSlots + disabledSlots + evSlots, price, regularSlots, disabledSlots, evSlots }),
    };

    try {
      const response = role === 'DRIVER' ? await signupDriver(userData) : await signupParkingLot(userData);

      navigate("/login")
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
    <div className="signup-container">
      <div className="signup-box">
        <h1>Smart City Parking</h1>
        <div className="role-buttons">
          <h2
            className={`role-button ${role === 'DRIVER' ? 'active' : ''}`}
            onClick={() => setRole('DRIVER')}
          >
            Driver Signup
          </h2>
          <h2
            className={`role-button ${role === 'MANAGEMENT' ? 'active' : ''}`}
            onClick={() => setRole('MANAGEMENT')}
          >
            Manager Signup
          </h2>
        </div>
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
          <div className="form-group">
            <label>Phone:</label>
            <input
              type="text"
              name="phone"
              value={phone}
              onChange={(e) => setPhone(e.target.value)}
            />
          </div>
          {role === 'DRIVER' && (
            <>
              <div className="form-group">
                <label>License Plate Number:</label>
                <input
                  type="text"
                  name="licensePlateNumber"
                  value={licensePlateNumber}
                  onChange={(e) => setLicensePlateNumber(e.target.value)}
                />
              </div>
              <div className="form-group">
                <label>Payment Method:</label>
                <select
                  name="paymentMethod"
                  value={paymentMethod}
                  onChange={(e) => setPaymentMethod(e.target.value)}
                >
                  <option value="CASH">CASH</option>
                  <option value="CREDIT_CARD">VISA</option>
                </select>
              </div>
            </>
          )}
          {role === 'MANAGEMENT' && (
            <>
              <label>Location:</label>
              <div className="form-group inline">
                <input
                  type="text"
                  name="city"
                  placeholder="City"
                  value={city}
                  onChange={(e) => setCity(e.target.value)}
                />
                <input
                  type="text"
                  name="street"
                  placeholder="Street"
                  value={street}
                  onChange={(e) => setStreet(e.target.value)}
                />
                <input
                  type="text"
                  name="locationLink"
                  placeholder="Location Link"
                  value={locationLink}
                  onChange={(e) => setLocationLink(e.target.value)}
                />
              </div>
              <label>Slots Types:</label>
              <div className="form-group inline">
                <input
                  type="number"
                  name="regularSlots"
                  placeholder='Regular'
                  value={regularSlots}
                  onChange={(e) => setRegularSlots(parseInt(e.target.value))}
                />
                <input
                  type="number"
                  name="disabledSlots"
                  placeholder='Disabled'
                  value={disabledSlots}
                  onChange={(e) => setDisabledSlots(parseInt(e.target.value))}
                />
                <input
                  type="number"
                  name="evSlots"
                  placeholder='EV'
                  value={evSlots}
                  onChange={(e) => setEvSlots(parseInt(e.target.value))}
                />
              </div>
              <div className="form-group">
                <label>Base Price:</label>
                <input
                  type="number"
                  name="price"
                  value={price}
                  onChange={(e) => setPrice(e.target.value)}
                />
              </div>
            </>
          )}
          <button type="submit" className="signup-button">Signup</button>
        </form>
        <button className="login-button" onClick={() => navigate('/login')}>Log In</button>
      </div>
    </div>
  );
}

export default Signup;