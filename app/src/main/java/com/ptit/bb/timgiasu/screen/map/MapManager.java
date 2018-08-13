package com.ptit.bb.timgiasu.screen.map;

import android.content.Context;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by BB on 12/15/2017.
 */
public class MapManager {

    private Context mContext;
    private GoogleMap mGoogleMap;

    public MapManager(Context mContext, GoogleMap mGoogleMap) {
        this.mContext = mContext;
        this.mGoogleMap = mGoogleMap;
        initMap();
    }

    public GoogleMap getGoogleMap() {
        return mGoogleMap;
    }

    public void addMarker(LatLng latLng, String title) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.draggable(true);
        markerOptions.position(latLng);
        markerOptions.title(title);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        mGoogleMap.addMarker(markerOptions);
        CameraUpdate cameraUpdateFactory = CameraUpdateFactory.newLatLngZoom(latLng, 16f);
        mGoogleMap.animateCamera(cameraUpdateFactory);
    }

    public void clearAllMarker() {
        mGoogleMap.clear();
    }

    private void initMap() {
        mGoogleMap.getUiSettings().setAllGesturesEnabled(true);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(false);
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.getUiSettings().setMapToolbarEnabled(true);
    }

}

