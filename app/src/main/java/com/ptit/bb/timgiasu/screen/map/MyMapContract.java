package com.ptit.bb.timgiasu.screen.map;

import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.gemvietnam.base.viper.interfaces.IInteractor;
import com.gemvietnam.base.viper.interfaces.IPresenter;
import com.gemvietnam.base.viper.interfaces.PresentView;
import com.ptit.bb.timgiasu.data.dto.Coord;

/**
 * The Map Contract
 */
interface MyMapContract {

    interface Interactor extends IInteractor<Presenter> {
    }

    interface View extends PresentView<Presenter> {
        void showSuggest();

        void showMarker(Coord location);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void suggestPlace(String key);

        ArrayAdapter getAdapter();

        void showPlace(String selectedSearchItem);

        void selectLocation(String selectedSearchItem);
    }
}



