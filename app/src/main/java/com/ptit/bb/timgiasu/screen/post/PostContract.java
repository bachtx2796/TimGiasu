package com.ptit.bb.timgiasu.screen.post;

import com.gemvietnam.base.viper.interfaces.IInteractor;
import com.gemvietnam.base.viper.interfaces.IPresenter;
import com.gemvietnam.base.viper.interfaces.PresentView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.ptit.bb.timgiasu.data.dto.PostDTO;

import java.util.List;

/**
 * The Post Contract
 */
interface PostContract {

    interface Interactor extends IInteractor<Presenter> {
        void newPost(String city, String idPost, PostDTO postDTO, OnCompleteListener<Void> onCompleteListener);
    }

    interface View extends PresentView<Presenter> {
        void setLocation(String location);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void newPost(String address,List<String> mClasses, List<String> mSubjects, String time, String salary, List<String> mListUri);

        void pickAddress();
    }
}



