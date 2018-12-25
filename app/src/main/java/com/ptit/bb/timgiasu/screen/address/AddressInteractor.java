package com.ptit.bb.timgiasu.screen.address;

import com.gemvietnam.base.viper.Interactor;

/**
 * The Address interactor
 */
class AddressInteractor extends Interactor<AddressContract.Presenter>
        implements AddressContract.Interactor {

    AddressInteractor(AddressContract.Presenter presenter) {
        super(presenter);
    }
}
