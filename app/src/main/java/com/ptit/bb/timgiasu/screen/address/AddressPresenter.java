package com.ptit.bb.timgiasu.screen.address;

import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;
import com.ptit.bb.timgiasu.data.dto.Coord;

/**
 * The Address Presenter
 */
public class AddressPresenter extends Presenter<AddressContract.View, AddressContract.Interactor>
        implements AddressContract.Presenter {

    private Coord coord1;
    private String title;

    public AddressPresenter setCoord(Coord coord, String title) {
        this.coord1 = coord;
        this.title = title;
        return this;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Coord getCoord1() {
        return coord1;
    }

    public AddressPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public AddressContract.View onCreateView() {
        return AddressFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public AddressContract.Interactor onCreateInteractor() {
        return new AddressInteractor(this);
    }
}
