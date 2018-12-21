
package com.ptit.bb.timgiasu.data.dto;

import com.google.gson.annotations.SerializedName;

public class Coord {

   // @SerializedName("lat")
    private double lat;
  //  @SerializedName("lng")
    private double lng;

    public Coord() {
    }

    public Coord(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "Coord{" +
                "lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
