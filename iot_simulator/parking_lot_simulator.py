from parking_slot_model import ParkingSlotModel
from database_connection import DatabaseConnection


class ParkingLotSimulator:
    def __init__(self, parking_lot_id):
        self.parking_lot_id = parking_lot_id
        self.db = DatabaseConnection()
        self.parking_slots = self.get_parking_slots()

    def get_parking_slots(self):
        query = "SELECT id FROM parking_spot WHERE parking_lot_id = %s"
        parking_spot_ids = self.db.query(query, (self.parking_lot_id,))
        return [ParkingSlotModel(parking_spot_id[0]) for parking_spot_id in parking_spot_ids]
    
    def run(self):
        for parking_slot in self.parking_slots:
            parking_slot.start()

    def stop(self):
        for parking_slot in self.parking_slots:
            parking_slot.stop()

if __name__ == "__main__":
    simulator = ParkingLotSimulator(1)
    simulator.run()
    input("Press Enter to stop the simulation")
    simulator.stop()
            