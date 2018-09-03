package com.ptit.bb.timgiasu.screen.tutorprofile;

import com.gemvietnam.base.viper.Interactor;

/**
 * The TutorProfile interactor
 */
class TutorProfileInteractor extends Interactor<TutorProfileContract.Presenter>
        implements TutorProfileContract.Interactor {

    TutorProfileInteractor(TutorProfileContract.Presenter presenter) {
        super(presenter);
    }
}
