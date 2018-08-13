package com.ptit.bb.timgiasu.screen.map;

import com.gemvietnam.base.viper.Interactor;

/**
 * The Map interactor
 */
class MapInteractor extends Interactor<MapContract.Presenter>
        implements MapContract.Interactor {

    MapInteractor(MapContract.Presenter presenter) {
        super(presenter);
    }
}
