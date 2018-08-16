package com.ptit.bb.timgiasu.data;

import com.ptit.bb.timgiasu.data.dto.GoogleMapSearchDTO;
import com.ptit.bb.timgiasu.data.dto.PredictionPlaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by BB on 8/29/2017.
 */

public interface GoogleService {

    String KEY ="AIzaSyCO4R8E2QQS3kGtvn0erWC7Fjec1FYDN_c";

    @GET("api/place/autocomplete/json")
    Call<PredictionPlaces> getLocationSuggestion(@Query("input") String input,
                                                 @Query("language") String lan,
                                                 @Query("key") String key);

    @GET("api/place/textsearch/json")
    Call<GoogleMapSearchDTO> getLocationSearch(@Query("query") String query,
                                               @Query("key") String key);


}
