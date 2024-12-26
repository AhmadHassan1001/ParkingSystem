import time
import datetime
import threading

from probability_model import ProbabilityModel
from database_connection import DatabaseConnection
from mysql.connector.locales.eng import client_error

START_TIME_INDEX = 3
END_TIME_INDEX = 4
ID_INDEX = 0


class ParkingSlotModel(threading.Thread):
  def __init__(self, slot_id):
    super().__init__()
    self.slot_id = slot_id
    self.upcoming_start = None
    self.upcoming_end = None
    self.upcoming_reservation_id = None
    self.old_status = None

  def run(self):
    while True:
      self.update_status()
      time.sleep(1)
  
  def stop(self):
    self.join()

  def get_upcoming_reservation(self):
    db = DatabaseConnection()
    query = "SELECT * FROM reservation WHERE parking_spot_id = %s AND start_time > NOW() ORDER BY start_time LIMIT 1"
    result = db.query(query, (self.slot_id,))

    return result
  
  def get_spot_interval(self):
    upcoming_reservation = self.get_upcoming_reservation()
    if len(upcoming_reservation) == 0:
      return
    
    if self.upcoming_reservation_id == upcoming_reservation[0][ID_INDEX] or (self.upcoming_end and self.upcoming_end > datetime.datetime.now()):
      # The upcoming reservation has not changed or the reservation is still ongoing
      return
    
    
    start_time = upcoming_reservation[0][START_TIME_INDEX]
    end_time = upcoming_reservation[0][END_TIME_INDEX]
    self.upcoming_reservation_id = upcoming_reservation[0]

    # Probability model for the start time
    start_time = datetime.datetime.strptime(str(start_time), "%Y-%m-%d %H:%M:%S")
    now = datetime.datetime.now()
    start_diff = start_time - now
    start_time_probability_model = ProbabilityModel(10, start_diff.total_seconds() / 3600)
    self.upcoming_start = now + datetime.timedelta(hours=start_time_probability_model.get_next_event_time())

    # Probability model for the end time
    end_time = datetime.datetime.strptime(str(end_time), "%Y-%m-%d %H:%M:%S")
    end_diff = end_time - start_time
    end_time_probability_model = ProbabilityModel(10, end_diff.total_seconds() / 3600)
    self.upcoming_end = self.upcoming_start + datetime.timedelta(hours=end_time_probability_model.get_next_event_time())

    print(f"Slot {self.slot_id} has a reservation from {self.upcoming_start} to {self.upcoming_end}")
  
  def is_reserved(self):
    self.get_spot_interval()
    return (self.upcoming_start is not None and self.upcoming_start <= datetime.datetime.now() <= self.upcoming_end)
  
  def update_status(self):
    is_reserved = self.is_reserved()
    if self.old_status != is_reserved:
      self.old_status = is_reserved
      print(f"Slot {self.slot_id} is reserved: {is_reserved}")
      # TODO: Update the status in the backend


if __name__ == "__main__":
  db = DatabaseConnection()
  
  parking_slot1 = ParkingSlotModel(1)
  parking_slot2 = ParkingSlotModel(2)
  parking_slot1.start()
  parking_slot2.start()
  time.sleep(300)
  parking_slot1.stop()
  parking_slot2.stop()
  print("Done")
