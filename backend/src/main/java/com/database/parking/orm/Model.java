package com.database.parking.orm;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Base model class for handling database operations.
 *
 * @param <T> the type of the model extending Model
 */
public class Model<T extends Model<T>> {
  public String primaryKey = "id";
  private MySQLConnection mySQLConnection;
  private Statement statement;

  // Setters for dependency injection
  public Model() {
    this.mySQLConnection = new MySQLConnection();
    this.statement = mySQLConnection.createStatement();
  }

  // Configurations

  /**
   * Sets the primary key field name.
   *
   * @param primaryKey the primary key field name
   * @example
   * User user = new User();
   * user.setPrimaryKey("userId");
   */
  public void setPrimaryKey(String primaryKey) {
    this.primaryKey = primaryKey;
  }

  /**
   * Gets the primary key field name.
   *
   * @return the primary key field name
   * @example
   * User user = new User();
   * String primaryKey = user.getPrimaryKey();
   */
  public String getPrimaryKey() {
    return this.primaryKey;
  }

  /**
   * Gets the value of the primary key field.
   *
   * @return the value of the primary key field
   * @example
   * User user = new User();
   * Object id = user.getId();
   */
  public Object getId() {
    Field[] fields = this.getClass().getDeclaredFields();
    for (Field field : fields) {
      if (field.getName().equals(primaryKey)) {
        field.setAccessible(true);
        try {
          return field.get(this);
        } catch (IllegalArgumentException | IllegalAccessException e) {
          e.printStackTrace();
        }
      }
    }
    return null;
  }

  // operations

  /**
   * Saves the current model instance to the database.
   *
   * @example
   * User user = new User();
   * user.setFirstName("John");
   * user.setLastName("Doe");
   * user.save();
   */
  public void save() {
    String query = "INSERT INTO " + getTableName() + " (";
    String values = "VALUES (";
    Field[] fields = this.getClass().getDeclaredFields();
    for (Field field : fields) {
      field.setAccessible(true);
      try {
        if (field.get(this) != null) {
          query += mapFieldToColumn(field) + ", ";
          values += "'" + field.get(this) + "', ";
        }
      } catch (IllegalArgumentException | IllegalAccessException e) {
        e.printStackTrace();
      }
    }
    query = query.substring(0, query.length() - 2) + ") ";
    values = values.substring(0, values.length() - 2) + ")";
    query += values;
    System.out.println(query);
    try {
      statement.executeUpdate(query);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Updates the current model instance in the database.
   *
   * @example
   * User user = new User();
   * user.setId(1);
   * user.setFirstName("John");
   * user.setLastName("Doe");
   * user.update();
   */
  public void update() {
    String query = "UPDATE " + getTableName() + " SET ";
    Field[] fields = this.getClass().getDeclaredFields();
    for (Field field : fields) {
      field.setAccessible(true);
      try {
        if (field.get(this) != null) {
          query += mapFieldToColumn(field) + " = '" + field.get(this) + "', ";
        }
      } catch (IllegalArgumentException | IllegalAccessException e) {
        e.printStackTrace();
      }
    }
    query = query.substring(0, query.length() - 2) + " ";
    query += "WHERE " + primaryKey + " = " + getId();
    System.out.println(query);
    try {
      statement.executeUpdate(query);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Deletes the current model instance from the database.
   *
   * @example
   * User user = new User();
   * user.setId(1);
   * user.delete();
   */
  public void delete() {
    String query = "DELETE FROM " + getTableName() + " WHERE " + primaryKey + " = " + getId();
    System.out.println(query);
    try {
      statement.executeUpdate(query);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // Query

  // utils

  private String mapFieldToColumn(Field field) {
    String columnName = field.getName();
    columnName = columnName.replaceAll("(.)(\\p{Upper})", "$1_$2").toLowerCase();
    return columnName;
  }

  private String getFieldType(Field field) {
    if (field.getType().equals(String.class)) {
      return "VARCHAR(255)";
    } else if (field.getType().equals(Integer.class)) {
      return "INT";
    } else if (field.getType().equals(Double.class)) {
      return "DOUBLE";
    } else if (field.getType().equals(Boolean.class)) {
      return "BOOLEAN";
    }
    return "";
  }

  /**
   * Gets the table name for the model.
   *
   * @return the table name
   * @example
   * User user = new User();
   * String tableName = user.getTableName();
   */
  public String getTableName() {
    String tableName = this.getClass().getSimpleName();
    tableName = tableName.replaceAll("(.)(\\p{Upper})", "$1_$2").toLowerCase() + "s";
    return tableName;
  }

  /**
   * Converts a ResultSet to a list of model instances.
   *
   * @param resultSet the ResultSet to convert
   * @return a list of model instances
   * @example
   * ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
   * User user = new User();
   * List<User> users = user.fromResultSet(resultSet);
   */
  public List<T> fromResultSet(ResultSet resultSet) {
    List<T> models = new ArrayList<>();
    try {
      while (resultSet.next()) {
        T model = (T) this.getClass().newInstance();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
          field.setAccessible(true);
          try {
            field.set(model, resultSet.getObject(mapFieldToColumn(field)));
          } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
          }
        }
        models.add(model);
      }
    } catch (InstantiationException | IllegalAccessException | SQLException e) {
      e.printStackTrace();
    }
    return models;
  }
}