package com.ptit.bb.timgiasu.screen.login;

import com.gemvietnam.base.viper.Interactor;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ptit.bb.timgiasu.Utils.DBConstan;

/**
 * The LoginFragment interactor
 */
class LoginInteractor extends Interactor<LoginContract.Presenter>
        implements LoginContract.Interactor {

    LoginInteractor(LoginContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void saveUser(String id, ValueEventListener valueEventListener) {
        FirebaseDatabase.getInstance().getReference(DBConstan.USERS).child(id).addListenerForSingleValueEvent(valueEventListener);
    }
}
