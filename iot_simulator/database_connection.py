import mysql.connector


class DatabaseConnection:
  def __init__(self):
    self.connection = mysql.connector.connect(host="localhost", user="root", password="root", database="parking_management_system")
  
  # def __del__(self):
  #   self.connection.close()

  def query(self, query, params):
    with self.connection.cursor() as cursor:
      cursor.execute(query, params)
      return cursor.fetchall()

if __name__ == "__main__":
  db = DatabaseConnection()
  print(db.query("SELECT * FROM parking_lot WHERE id = %s", (1,)))
