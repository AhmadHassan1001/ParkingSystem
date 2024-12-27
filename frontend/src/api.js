import client from "./client";

// Auth
export const login = async (email, password) => {
  const response = await client.post("/auth/login", { email, password });
  window.localStorage.setItem("token", response.data.token);
  console.log(response.data.token);
  console.log(window.localStorage.getItem('token'));
  return response.data;
}

// Parking Lots
export const parkingLotList = async () => {
  const response = await client.get("/parking-lots");
  return response.data;
}

export const parkingLotDetails = async (id) => {
  const response = await client.get(`/parking-lots/${id}`);
  return response.data;
}

export const createParkingLot = async (parkingLot) => {
  const response = await client.post("/parking-lots", parkingLot);
  return response.data;
}

export const updateParkingLot = async (id, parkingLot) => {
  const response = await client.put(`/parking-lots/${id}`, parkingLot);
  return response.data;
}

// Parking Spots
export const parkingSpotList = async () => {
  const response = await client.get("/parking-spots");
  return response.data;
}

export const parkingSpotDetails = async (id) => {
  const response = await client.get(`/parking-spots/${id}`);
  return response.data;
}

// Reservations
export const reserveSpot = async (spotId, startTime, endTime) => {
  const response = await client.post(`/reserve/${spotId}`, { startTime, endTime });
  return response.data;
}

export const calculatePrice = async (spotId, startTime, endTime) => {
  const response = await client.post(`/calculate-price/${spotId}`, { startTime, endTime });
  return response.data;
}

// Locations
export const locationList = async () => {
  const response = await client.get("/locations");
  return response.data;
}