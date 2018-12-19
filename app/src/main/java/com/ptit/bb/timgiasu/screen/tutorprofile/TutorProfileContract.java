package com.ptit.bb.timgiasu.screen.tutorprofile;

import com.gemvietnam.base.viper.interfaces.IInteractor;
import com.gemvietnam.base.viper.interfaces.IPresenter;
import com.gemvietnam.base.viper.interfaces.PresentView;
import com.ptit.bb.timgiasu.data.dto.UserDTO;

import java.util.HashMap;

/**
 * The TutorProfile Contract
 */
interface TutorProfileContract {

    interface Interactor extends IInteractor<Presenter> {
        void saveRating(String id, String city, HashMap<String, Integer> rating);
    }

    interface View extends PresentView<Presenter> {
        void bindTutor(UserDTO mUser);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void contact();

        void saveRating(float rate);

        UserDTO getmUser();
    }
}



