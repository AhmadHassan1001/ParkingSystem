package com.database.parking.orm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Repository class for handling database operations for models.
 *
 * @param <T> the type of the model extending Model
 */
public class Repo<T extends Model> {
  private final T model;
  private final MySQLConnection connection = new MySQLConnection();
  private final Statement statement = connection.createStatement();
  private final String tableName;

  public Repo(T model) {
    this.model = model;
    this.tableName = model.getTableName();
  }

  /**
   * Retrieves all records from the table.
   *
   * @return a list of all records
   * @example
   * Repo<User> userRepo = new Repo<>(new User());
   * List<User> users = userRepo.all();
   */
  public List<T> all() {
    ResultSet resultSet;
    try {
      resultSet = statement.executeQuery("SELECT * FROM " + tableName);
      return model.fromResultSet(resultSet);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return new ArrayList<>();
  }

  /**
   * Finds a record by its ID.
   *
   * @param id the ID of the record
   * @return the record with the specified ID, or null if not found
   * @example
   * Repo<User> userRepo = new Repo<>(new User());
   * User user = userRepo.find(1L);
   */
  public T find(Long id) {
    ResultSet resultSet;
    try {
      resultSet = statement.executeQuery("SELECT * FROM " + tableName + " WHERE " + model.getPrimaryKey() + " = " + id);
      return (T) model.fromResultSet(resultSet).get(0);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Finds records matching the specified conditions.
   *
   * @param conditions a map of field names and values to match
   * @return a list of records matching the conditions
   * @example
   * Repo<User> userRepo = new Repo<>(new User());
   * HashMap<String, String> conditions = new HashMap<>();
   * conditions.put("firstName", "John");
   * conditions.put("lastName", "Doe");
   * List<User> users = userRepo.where(conditions);
   */
  public List<T> where(HashMap<String, String> conditions) {
    ResultSet resultSet;
    String query = "SELECT * FROM " + tableName + " WHERE ";
    for (Map.Entry<String, String> entry : conditions.entrySet()) {
      query += mapFieldToColumn(entry.getKey()) + " = '" + entry.getValue() + "' AND ";
    }
    query = query.substring(0, query.length() - 5);
    try {
      resultSet = statement.executeQuery(query);
      return model.fromResultSet(resultSet);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return new ArrayList<>();
  }

  /**
   * Finds records matching the specified query.
   *
   * @param query the query string
   * @return a list of records matching the query
   * @example
   * Repo<User> userRepo = new Repo<>(new User());
   * List<User> users = userRepo.where("age > 30");
   */
  public List<T> where(String query) {
    ResultSet resultSet;
    try {
      resultSet = statement.executeQuery("SELECT * FROM " + tableName + " WHERE " + query);
      return model.fromResultSet(resultSet);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return new ArrayList<>();
  }

  private String mapFieldToColumn(String fieldName) {
    // Convert camelCase to snake_case
    String columnName = fieldName;
    columnName = columnName.replaceAll("(.)(\\p{Upper})", "$1_$2").toLowerCase();
    return columnName;
  }
}