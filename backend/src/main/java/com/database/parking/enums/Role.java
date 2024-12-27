package com.database.parking.enums;

public enum Role {
  ADMIN("admin"),
  DRIVER("driver"),
  LOT_MANAGER("lot_manager");

  private final String value;

  Role(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
