package com.ptit.bb.timgiasu.data.dto;

import com.google.gson.annotations.SerializedName;

public class GeoPosition {
//    @SerializedName("Elevation")
//    private Elevation elevation;
    @SerializedName("Latitude")
    private double latitude;
    @SerializedName("Longitude")
    private double longitude;

//    public void setElevation(Elevation elevation) {
//        this.elevation = elevation;
//    }
//
//    public Elevation getElevation() {
//        return elevation;
//    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
