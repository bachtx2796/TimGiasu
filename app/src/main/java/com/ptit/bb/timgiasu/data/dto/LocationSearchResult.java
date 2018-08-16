package com.ptit.bb.timgiasu.data.dto;

import com.google.gson.annotations.SerializedName;


/**
 * Created by HaiLS on 10/11/2017.
 */

public class LocationSearchResult {

  @SerializedName("place_id")
  private String mPlaceId;
  @SerializedName("formatted_address")
  private String mFormattedAddress;
  @SerializedName("geometry")
  private Geometry mGeometry;

  public String getPlaceId() {
    return mPlaceId;
  }

  public void setPlaceId(String mPlaceId) {
    this.mPlaceId = mPlaceId;
  }

  public String getFormattedAddress() {
    return mFormattedAddress;
  }

  public void setFormattedAddress(String mFormattedAddress) {
    this.mFormattedAddress = mFormattedAddress;
  }

  public Geometry getGeometry() {
    return mGeometry;
  }

  public void setGeometry(Geometry mGeometry) {
    this.mGeometry = mGeometry;
  }

}
