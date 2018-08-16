package com.ptit.bb.timgiasu.data.dto;

import com.google.gson.annotations.SerializedName;

public class LocationDTO {
  @SerializedName("LocalizedName")
  private String localizedName;
  @SerializedName("GeoPosition")
  private GeoPosition geoPosition;
  @SerializedName("Key")
  private String key;
  @SerializedName("Country")
  private Country country;

  public Country getCountry() {
    return country;
  }

  public void setCountry(Country country) {
    this.country = country;
  }

  public String getLocalizedName() {
    return localizedName;
  }

  public GeoPosition getGeoPosition() {
    return geoPosition;
  }

  public String getKey() {
    return key;
  }

  public void setLocalizedName(String localizedName) {
    this.localizedName = localizedName;
  }

  public void setGeoPosition(GeoPosition geoPosition) {
    this.geoPosition = geoPosition;
  }

  public void setKey(String key) {
    this.key = key;
  }

}