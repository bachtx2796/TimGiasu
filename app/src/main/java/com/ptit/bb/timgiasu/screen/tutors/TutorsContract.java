package com.ptit.bb.timgiasu.screen.tutors;

import com.gemvietnam.base.viper.interfaces.IInteractor;
import com.gemvietnam.base.viper.interfaces.IPresenter;
import com.gemvietnam.base.viper.interfaces.PresentView;
import com.google.firebase.database.ValueEventListener;

/**
 * The Tutors Contract
 */
interface TutorsContract {

    interface Interactor extends IInteractor<Presenter> {
        void getTutors(String city, ValueEventListener valueEventListener);
    }

    interface View extends PresentView<Presenter> {
        void bindView(TutorAdapter mTutorAdapter);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void getData();

        void filter();
    }
}



