package com.ptit.bb.timgiasu.screen.profile;

import com.gemvietnam.base.viper.Interactor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.ptit.bb.timgiasu.Utils.DBConstan;
import com.ptit.bb.timgiasu.data.dto.UserDTO;

/**
 * The Profile interactor
 */
class ProfileInteractor extends Interactor<ProfileContract.Presenter>
        implements ProfileContract.Interactor {

    ProfileInteractor(ProfileContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void updateEmail(String email, OnCompleteListener<Void> onCompleteListener) {
        FirebaseAuth.getInstance().getCurrentUser().updateEmail(email).addOnCompleteListener(onCompleteListener);
    }

    @Override
    public void saveInfo(String id, UserDTO mUser, OnCompleteListener<Void> onCompleteListener) {
        FirebaseDatabase.getInstance().getReference(DBConstan.USERS).child(id).setValue(mUser).addOnCompleteListener(onCompleteListener);
    }

    @Override
    public Task<Void> saveTutor(UserDTO user, OnCompleteListener<Void> onCompleteListener) {
        return FirebaseDatabase.getInstance().getReference(DBConstan.USERS).child(user.getId()).setValue(user).addOnCompleteListener(onCompleteListener);
    }
}
