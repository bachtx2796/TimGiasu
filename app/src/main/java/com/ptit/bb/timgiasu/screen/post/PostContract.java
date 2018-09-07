package com.ptit.bb.timgiasu.screen.post;

import com.gemvietnam.base.viper.interfaces.IInteractor;
import com.gemvietnam.base.viper.interfaces.IPresenter;
import com.gemvietnam.base.viper.interfaces.PresentView;

import java.util.List;

/**
 * The Post Contract
 */
interface PostContract {

    interface Interactor extends IInteractor<Presenter> {
    }

    interface View extends PresentView<Presenter> {
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void newPost(String address,List<String> mClasses, List<String> mSubjects, String time, String salary, List<String> mListUri);
    }
}



