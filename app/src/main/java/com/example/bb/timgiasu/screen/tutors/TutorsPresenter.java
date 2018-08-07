package com.example.bb.timgiasu.screen.tutors;

import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;

/**
 * The Tutors Presenter
 */
public class TutorsPresenter extends Presenter<TutorsContract.View, TutorsContract.Interactor>
        implements TutorsContract.Presenter {

    public TutorsPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public TutorsContract.View onCreateView() {
        return TutorsFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public TutorsContract.Interactor onCreateInteractor() {
        return new TutorsInteractor(this);
    }
}
