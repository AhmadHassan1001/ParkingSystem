import os
import requests

from dotenv import load_dotenv

from parking_slot_model import ParkingSlotModel
from database_connection import DatabaseConnection


load_dotenv()

USERNAME = os.getenv("username")
PASSWORD = os.getenv("password")
LOT_ID = int(os.getenv("lot_id"))

BASE_URL = "http://localhost:8080"


class ParkingLotSimulator:
    def __init__(self, parking_lot_id):
        self.parking_lot_id = parking_lot_id
        self.db = DatabaseConnection()
        self.token = self.get_token()
        self.parking_slots = self.get_parking_slots()

    def get_parking_slots(self):
        response = requests.get(f"{BASE_URL}/parking-lots/{self.parking_lot_id}", headers={"Authorization": f"Bearer {self.token}"})
        parking_spot_ids = [ spot["id"] for spot in response.json()["parkingSpots"]]
        print(parking_spot_ids)
        return [ParkingSlotModel(parking_spot_id, self.token) for parking_spot_id in parking_spot_ids]
    
    def get_token(self):
        payload = {
            "name": USERNAME,
            "password": PASSWORD
        }
        response = requests.post(f"{BASE_URL}/auth/login", json=payload).json()
        return response["token"]

    def run(self):
        for parking_slot in self.parking_slots:
            parking_slot.start()

    def stop(self):
        for parking_slot in self.parking_slots:
            parking_slot.stop()

if __name__ == "__main__":
    simulator = ParkingLotSimulator(LOT_ID)
    simulator.run()
    input("Press Enter to stop the simulation")
    simulator.stop()
            