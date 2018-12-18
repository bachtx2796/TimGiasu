package com.ptit.bb.timgiasu.screen.home;

import com.gemvietnam.base.viper.Interactor;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ptit.bb.timgiasu.Utils.DBConstan;

/**
 * The Home interactor
 */
class HomeInteractor extends Interactor<HomeContract.Presenter>
        implements HomeContract.Interactor {

    HomeInteractor(HomeContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void getPosts(String city, ValueEventListener valueEventListener) {
        FirebaseDatabase.getInstance().getReference(DBConstan.CITIES).child(city).child(DBConstan.POSTS).addValueEventListener(valueEventListener);
    }
}
