package com.ptit.bb.timgiasu.screen.chatdetail;

import com.gemvietnam.base.viper.Interactor;
import com.ptit.bb.timgiasu.data.dto.PushNotificationDTO;
import com.ptit.bb.timgiasu.pushnotification.PushNotificationServiceBuilder;

import retrofit2.Callback;

/**
 * The ChatDetail interactor
 */
class ChatDetailInteractor extends Interactor<ChatDetailContract.Presenter>
        implements ChatDetailContract.Interactor {

    ChatDetailInteractor(ChatDetailContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void pushNotification(PushNotificationDTO pushNotificationDTO, Callback<Object> callback) {
        PushNotificationServiceBuilder.getService().pushNotification(pushNotificationDTO).enqueue(callback);
    }
}
