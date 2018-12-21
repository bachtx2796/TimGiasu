package com.ptit.bb.timgiasu.screen.map;

import android.widget.ArrayAdapter;

import com.gemvietnam.base.viper.interfaces.IInteractor;
import com.gemvietnam.base.viper.interfaces.IPresenter;
import com.gemvietnam.base.viper.interfaces.PresentView;
import com.ptit.bb.timgiasu.data.dto.Coord;
import com.ptit.bb.timgiasu.data.dto.GoogleMapSearchDTO;
import com.ptit.bb.timgiasu.data.dto.PredictionPlaces;

import retrofit2.Callback;

/**
 * The Map Contract
 */
interface MyMapContract {

    interface Interactor extends IInteractor<Presenter> {
        void suggestPlace(String key, Callback<PredictionPlaces> callback);

        void showPlace(String selectedSearchItem, Callback<GoogleMapSearchDTO> callback);
    }

    interface View extends PresentView<Presenter> {
        void showSuggest();

        void showMarker(Coord location);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void suggestPlace(String key);

        ArrayAdapter getAdapter();

        void showPlace(String selectedSearchItem);

        void selectLocation(String selectedSearchItem, Coord mCoord);
    }
}



