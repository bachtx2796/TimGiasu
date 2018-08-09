package com.ptit.bb.timgiasu.screen.favourite;

import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;

/**
 * The Favourite Presenter
 */
public class FavouritePresenter extends Presenter<FavouriteContract.View, FavouriteContract.Interactor>
        implements FavouriteContract.Presenter {

    public FavouritePresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public FavouriteContract.View onCreateView() {
        return FavouriteFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public FavouriteContract.Interactor onCreateInteractor() {
        return new FavouriteInteractor(this);
    }
}
