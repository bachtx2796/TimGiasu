package com.ptit.bb.timgiasu.screen.postdetail;

import android.support.annotation.NonNull;

import com.gemvietnam.base.viper.Interactor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.ptit.bb.timgiasu.Utils.DBConstan;
import com.ptit.bb.timgiasu.data.dto.PushNotificationDTO;
import com.ptit.bb.timgiasu.prewrapper.PrefWrapper;
import com.ptit.bb.timgiasu.pushnotification.PushNotificationServiceBuilder;

import retrofit2.Callback;

/**
 * The PostDetail interactor
 */
class PostDetailInteractor extends Interactor<PostDetailContract.Presenter>
    implements PostDetailContract.Interactor {

  PostDetailInteractor(PostDetailContract.Presenter presenter) {
    super(presenter);
  }

  @Override
  public void sendRequest(PushNotificationDTO pushNotificationDTO, Callback<Object> callback) {
    PushNotificationServiceBuilder.getService().pushNotification(pushNotificationDTO).enqueue(callback);
  }

  @Override
  public void closePost(String userID, int position, String city, String postID, String status, OnCompleteListener onCompleteListener) {
    FirebaseDatabase.getInstance().getReference()
        .child(DBConstan.CITIES)
        .child(city)
        .child(DBConstan.POSTS)
        .child(postID)
        .child(DBConstan.STATUS)
        .setValue(status).addOnCompleteListener(onCompleteListener);

    FirebaseDatabase.getInstance().getReference()
        .child(DBConstan.USERS)
        .child(userID)
        .child(DBConstan.MY_POST)
        .child(position + "")
        .child(DBConstan.STATUS)
        .setValue(status);
  }
}
