import client from "./client";

// Auth
export const login = async (name, password) => {
  const response = await client.post("/auth/login", { name, password });
  window.localStorage.setItem("token", response.data.token);
  return response.data;
}

export const signupDriver = async (userData) => {
  const response = await client.post("/auth/signup/driver", userData);
  return response.data;
}

export const signupParkingLot = async (userData) => {
  const response = await client.post("/auth/signup/parking-lot", userData);
  return response.data;
}

export const info = async () => {
  const response = await client.get("/auth/info");
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
  const response = await client.post(`/reservation/${spotId}/reserve`, { startTime, endTime });
  return response.data;
}

export const calculatePrice = async (spotId, startTime, endTime) => {
  const response = await client.post(`/reservation/${spotId}/calculate-cost`, { startTime, endTime });
  return response.data;
}

export const reservationPaid = async (reservationId) => {
  const response = await client.post(`/reservation/${reservationId}/pay`);
  return response.data;
}

// Locations
export const locationList = async () => {
  const response = await client.get("/locations");
  return response.data;
}