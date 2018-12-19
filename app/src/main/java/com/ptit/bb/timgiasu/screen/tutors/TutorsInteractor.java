package com.ptit.bb.timgiasu.screen.tutors;

import com.gemvietnam.base.viper.Interactor;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ptit.bb.timgiasu.Utils.DBConstan;
import com.ptit.bb.timgiasu.prewrapper.PrefWrapper;

/**
 * The Tutors interactor
 */
class TutorsInteractor extends Interactor<TutorsContract.Presenter>
        implements TutorsContract.Interactor {

    TutorsInteractor(TutorsContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void getTutors(String city, ValueEventListener valueEventListener) {
        FirebaseDatabase.getInstance().getReference(DBConstan.CITIES).child(city).child(DBConstan.USERS).addListenerForSingleValueEvent(valueEventListener);
    }
}
