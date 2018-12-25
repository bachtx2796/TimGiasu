package com.ptit.bb.timgiasu.screen.address;

import com.gemvietnam.base.viper.interfaces.IInteractor;
import com.gemvietnam.base.viper.interfaces.IPresenter;
import com.gemvietnam.base.viper.interfaces.PresentView;
import com.ptit.bb.timgiasu.data.dto.Coord;

/**
 * The Address Contract
 */
interface AddressContract {

    interface Interactor extends IInteractor<Presenter> {
    }

    interface View extends PresentView<Presenter> {
    }

    interface Presenter extends IPresenter<View, Interactor> {
        String getTitle();

        Coord getCoord1();
    }
}



