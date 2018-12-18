package com.ptit.bb.timgiasu.screen.map;

import com.gemvietnam.base.viper.Interactor;
import com.ptit.bb.timgiasu.data.GoogleService;
import com.ptit.bb.timgiasu.data.GoogleServiceBuilder;
import com.ptit.bb.timgiasu.data.dto.GoogleMapSearchDTO;
import com.ptit.bb.timgiasu.data.dto.PredictionPlaces;

import retrofit2.Callback;

/**
 * The Map interactor
 */
class MyMapInteractor extends Interactor<MyMapContract.Presenter>
        implements MyMapContract.Interactor {

    MyMapInteractor(MyMapContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void suggestPlace(String key, Callback<PredictionPlaces> callback) {
        GoogleServiceBuilder.getService().getLocationSuggestion(key, "vi", GoogleService.KEY).enqueue(callback);
    }

    @Override
    public void showPlace(String selectedSearchItem, Callback<GoogleMapSearchDTO> callback) {
        GoogleServiceBuilder.getService().getLocationSearch(selectedSearchItem, GoogleService.KEY).enqueue(callback);
    }
}
