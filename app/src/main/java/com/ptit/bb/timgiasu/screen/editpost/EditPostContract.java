package com.ptit.bb.timgiasu.screen.editpost;

import com.gemvietnam.base.viper.interfaces.IInteractor;
import com.gemvietnam.base.viper.interfaces.IPresenter;
import com.gemvietnam.base.viper.interfaces.PresentView;
import com.ptit.bb.timgiasu.data.dto.PostDTO;

import java.util.List;

/**
 * The EditPost Contract
 */
interface EditPostContract {

    interface Interactor extends IInteractor<Presenter> {
    }

    interface View extends PresentView<Presenter> {
        void bindPost(PostDTO mPost);

        void setLocation(String location);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void pickAddress();

        void savePost(String address, List<String> mClasses, List<String> mSubjects, String time, String salary, List<String> mListUri);
    }
}



