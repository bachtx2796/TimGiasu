package com.ptit.bb.timgiasu.screen.postdetail;

import com.gemvietnam.base.viper.interfaces.IInteractor;
import com.gemvietnam.base.viper.interfaces.IPresenter;
import com.gemvietnam.base.viper.interfaces.PresentView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.ptit.bb.timgiasu.data.dto.PostDTO;
import com.ptit.bb.timgiasu.data.dto.PushNotificationDTO;

import retrofit2.Callback;

/**
 * The PostDetail Contract
 */
interface PostDetailContract {

  interface Interactor extends IInteractor<Presenter> {
    void sendRequest(PushNotificationDTO pushNotificationDTO, Callback<Object> callback);

    void closePost(String userID, int position, String city, String postID, String status, OnCompleteListener onCompleteListener);
  }

  interface View extends PresentView<Presenter> {
    void bindView(PostDTO mPost);

    void sendRequestSuccess();
  }

  interface Presenter extends IPresenter<View, Interactor> {
    void editPost();

    void sentRequest();

    void createGrChat();

    void closePost();
  }
}



