package com.ptit.bb.timgiasu.data.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by BB on 4/7/2018.
 */

public class PredictionPlaces {

    @SerializedName("predictions")
    private List<SugguestLocation> mSugguestLocations;

    public PredictionPlaces(List<SugguestLocation> mSugguestLocations) {
        this.mSugguestLocations = mSugguestLocations;
    }

    public List<SugguestLocation> getmSugguestLocations() {
        return mSugguestLocations;
    }

    public void setmSugguestLocations(List<SugguestLocation> mSugguestLocations) {
        this.mSugguestLocations = mSugguestLocations;
    }
}
