package com.ptit.bb.timgiasu.screen.tutorprofile;

import com.gemvietnam.base.viper.Interactor;
import com.google.firebase.database.FirebaseDatabase;
import com.ptit.bb.timgiasu.Utils.DBConstan;

import java.util.HashMap;

/**
 * The TutorProfile interactor
 */
class TutorProfileInteractor extends Interactor<TutorProfileContract.Presenter>
        implements TutorProfileContract.Interactor {

    TutorProfileInteractor(TutorProfileContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void saveRating(String id, String city, HashMap<String, Integer> rating) {
        FirebaseDatabase.getInstance().getReference(DBConstan.USERS).child(id).child(DBConstan.RATINGS).setValue(rating);
        FirebaseDatabase.getInstance().getReference(DBConstan.CITIES).child(city).child(DBConstan.USERS).child(id).child(DBConstan.RATINGS).setValue(rating);
    }
}
