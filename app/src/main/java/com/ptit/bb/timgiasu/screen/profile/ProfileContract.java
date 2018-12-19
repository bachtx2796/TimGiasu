package com.ptit.bb.timgiasu.screen.profile;

import com.gemvietnam.base.viper.interfaces.IInteractor;
import com.gemvietnam.base.viper.interfaces.IPresenter;
import com.gemvietnam.base.viper.interfaces.PresentView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.ptit.bb.timgiasu.data.dto.UserDTO;

import java.util.List;

/**
 * The Profile Contract
 */
interface ProfileContract {

    interface Interactor extends IInteractor<Presenter> {
        void updateEmail(String email, OnCompleteListener<Void> onCompleteListener);

        void saveInfo(String id, UserDTO mUser, OnCompleteListener<Void> onCompleteListener);

        Task<Void> saveTutor(UserDTO user, OnCompleteListener<Void> onCompleteListener);
    }

    interface View extends PresentView<Presenter> {
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void logout();

        void saveUser(UserDTO mUser, boolean setEmail, boolean setCity);

        void changePassword();

        void saveTutor(List<String> mClasses, List<String> mSubjects, String time, String salary, List<String> mListUri);

        void displayHistory();
    }
}



