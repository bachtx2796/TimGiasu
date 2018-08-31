package com.ptit.bb.timgiasu.screen.profile;

import com.gemvietnam.base.viper.interfaces.IInteractor;
import com.gemvietnam.base.viper.interfaces.IPresenter;
import com.gemvietnam.base.viper.interfaces.PresentView;
import com.ptit.bb.timgiasu.data.dto.UserDTO;

import java.util.List;

/**
 * The Profile Contract
 */
interface ProfileContract {

    interface Interactor extends IInteractor<Presenter> {
    }

    interface View extends PresentView<Presenter> {
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void logout();

        void saveUser(UserDTO mUser);

        void changePassword();

        void saveTutor(List<String> mClasses, List<String> mSubjects, String time, String salary, List<String> mListUri);
    }
}



