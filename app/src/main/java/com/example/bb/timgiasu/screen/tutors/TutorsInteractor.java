package com.example.bb.timgiasu.screen.tutors;

import com.gemvietnam.base.viper.Interactor;

/**
 * The Tutors interactor
 */
class TutorsInteractor extends Interactor<TutorsContract.Presenter>
        implements TutorsContract.Interactor {

    TutorsInteractor(TutorsContract.Presenter presenter) {
        super(presenter);
    }
}
