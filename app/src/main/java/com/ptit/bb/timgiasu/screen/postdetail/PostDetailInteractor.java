package com.ptit.bb.timgiasu.screen.postdetail;

import com.gemvietnam.base.viper.Interactor;
import com.ptit.bb.timgiasu.data.dto.PushNotificationDTO;
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
}
