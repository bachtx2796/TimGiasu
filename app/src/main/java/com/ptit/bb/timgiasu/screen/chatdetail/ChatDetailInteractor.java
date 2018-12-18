package com.ptit.bb.timgiasu.screen.chatdetail;

import com.gemvietnam.base.viper.Interactor;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ptit.bb.timgiasu.Utils.DBConstan;
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

    @Override
    public void getInfo(String idUser, ValueEventListener valueEventListener) {
        FirebaseDatabase.getInstance().getReference(DBConstan.USERS).child(idUser).addListenerForSingleValueEvent(valueEventListener);
    }

    @Override
    public void getInfoPost(String city, String idPost, ValueEventListener valueEventListener) {
        FirebaseDatabase.getInstance().getReference(DBConstan.CITIES).child(city).child(DBConstan.POSTS).child(idPost).addListenerForSingleValueEvent(valueEventListener);
    }
}
