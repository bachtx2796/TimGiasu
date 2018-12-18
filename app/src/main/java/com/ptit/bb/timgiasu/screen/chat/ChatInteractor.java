package com.ptit.bb.timgiasu.screen.chat;

import com.gemvietnam.base.viper.Interactor;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ptit.bb.timgiasu.Utils.DBConstan;

/**
 * The Chat interactor
 */
class ChatInteractor extends Interactor<ChatContract.Presenter>
        implements ChatContract.Interactor {

    ChatInteractor(ChatContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void getListGroup(String id, ValueEventListener valueEventListener) {
        FirebaseDatabase.getInstance().getReference(DBConstan.USERS).child(id).child(DBConstan.GR_CHAT).addListenerForSingleValueEvent(valueEventListener);
    }
}
