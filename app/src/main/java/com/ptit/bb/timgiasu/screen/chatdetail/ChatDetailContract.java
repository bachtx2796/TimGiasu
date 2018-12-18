package com.ptit.bb.timgiasu.screen.chatdetail;

import android.content.Intent;

import com.gemvietnam.base.viper.interfaces.IInteractor;
import com.gemvietnam.base.viper.interfaces.IPresenter;
import com.gemvietnam.base.viper.interfaces.PresentView;
import com.google.firebase.database.ValueEventListener;
import com.ptit.bb.timgiasu.data.dto.PostDTO;
import com.ptit.bb.timgiasu.data.dto.PushNotificationDTO;

import retrofit2.Callback;

/**
 * The ChatDetail Contract
 */
interface ChatDetailContract {

    interface Interactor extends IInteractor<Presenter> {
        void pushNotification(PushNotificationDTO pushNotificationDTO, Callback<Object> callback);

        void getInfo(String idUser, ValueEventListener valueEventListener);

        void getInfoPost(String city, String idPost, ValueEventListener valueEventListener);
    }

    interface View extends PresentView<Presenter> {
        void bindPost(PostDTO mPost, String action);

        void bindUsername(String name);

        void bindListMsg(ChatAdapter mAdapter);

        void updateListMsg(int size);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void sendMsg(String s);

        void removeListener();

        void uploadImageWithFb(Intent data);

        void viewPost();

        void callOwner();

        void pushNotiDecline();

        void pushNotiAcepted();
    }
}



