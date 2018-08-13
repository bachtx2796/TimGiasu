package com.ptit.bb.timgiasu.screen.map;

import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;

/**
 * The Map Presenter
 */
public class MapPresenter extends Presenter<MapContract.View, MapContract.Interactor>
        implements MapContract.Presenter {

    public MapPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public MapContract.View onCreateView() {
        return MapFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public MapContract.Interactor onCreateInteractor() {
        return new MapInteractor(this);
    }
}
