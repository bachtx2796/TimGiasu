
package com.ptit.bb.timgiasu.data.dto;

import com.google.gson.annotations.SerializedName;

public class Geometry {

    @SerializedName("location")
    private Coord mLocation;
//    @SerializedName("viewport")
//    private Viewport mViewport;

    public Coord getLocation() {
        return mLocation;
    }

    public void setLocation(Coord location) {
        mLocation = location;
    }

//    public Viewport getViewport() {
//        return mViewport;
//    }
//
//    public void setViewport(Viewport viewport) {
//        mViewport = viewport;
//    }

}
