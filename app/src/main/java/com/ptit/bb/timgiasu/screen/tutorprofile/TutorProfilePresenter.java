package com.ptit.bb.timgiasu.screen.tutorprofile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;

import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;
import com.google.firebase.database.FirebaseDatabase;
import com.ptit.bb.timgiasu.Utils.DBConstan;
import com.ptit.bb.timgiasu.data.dto.UserDTO;
import com.ptit.bb.timgiasu.prewrapper.PrefWrapper;

import java.util.HashMap;

/**
 * The TutorProfile Presenter
 */
public class TutorProfilePresenter extends Presenter<TutorProfileContract.View, TutorProfileContract.Interactor>
        implements TutorProfileContract.Presenter {

    private UserDTO mUser;

    public TutorProfilePresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public TutorProfileContract.View onCreateView() {
        return TutorProfileFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
        mView.bindTutor(mUser);
    }

    @Override
    public TutorProfileContract.Interactor onCreateInteractor() {
        return new TutorProfileInteractor(this);
    }

    public TutorProfilePresenter setTutor(UserDTO userDTO) {
        mUser = userDTO;
        return this;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void contact() {
        String uri = "tel:" + mUser.getPhoneNo();
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(uri));
        getViewContext().startActivity(intent);
    }

    @Override
    public void saveRating(float rate) {
        HashMap<String, Integer> rating;
        if (mUser.getRatings() == null) {
            rating = new HashMap<>();
        } else {
            rating = mUser.getRatings();
        }
        rating.put(PrefWrapper.getUser(getViewContext()).getId(), (int) rate);
        FirebaseDatabase.getInstance().getReference(DBConstan.USERS).child(mUser.getId()).child(DBConstan.RATINGS).setValue(rating);
        FirebaseDatabase.getInstance().getReference(DBConstan.CITIES).child(mUser.getCity()).child(DBConstan.USERS).child(mUser.getId()).child(DBConstan.RATINGS).setValue(rating);
    }

    @Override
    public UserDTO getmUser() {
        return mUser;
    }
}
