package com.ptit.bb.timgiasu.screen.favourite;

import com.gemvietnam.base.viper.Interactor;

/**
 * The Favourite interactor
 */
class FavouriteInteractor extends Interactor<FavouriteContract.Presenter>
        implements FavouriteContract.Interactor {

    FavouriteInteractor(FavouriteContract.Presenter presenter) {
        super(presenter);
    }
}
