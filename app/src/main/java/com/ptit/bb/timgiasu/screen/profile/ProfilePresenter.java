package com.ptit.bb.timgiasu.screen.profile;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;
import com.gemvietnam.utils.ActivityUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.ptit.bb.timgiasu.Utils.DBConstan;
import com.ptit.bb.timgiasu.data.dto.UserDTO;
import com.ptit.bb.timgiasu.prewrapper.PrefWrapper;
import com.ptit.bb.timgiasu.screen.login.LoginActivity;

/**
 * The Profile Presenter
 */
public class ProfilePresenter extends Presenter<ProfileContract.View, ProfileContract.Interactor>
        implements ProfileContract.Presenter {

    private static final String TAG = ProfilePresenter.class.getSimpleName();

    public ProfilePresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public ProfileContract.View onCreateView() {
        return ProfileFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public ProfileContract.Interactor onCreateInteractor() {
        return new ProfileInteractor(this);
    }

    @Override
    public void logout() {
        FirebaseAuth.getInstance().signOut();
        PrefWrapper.saveUser(getViewContext(), null);
        ActivityUtils.startActivity(getViewContext(), LoginActivity.class);
        getViewContext().finish();
    }

    @Override
    public void saveUser(final UserDTO mUser) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.updateEmail(mUser.getEmail())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User email address updated.");
                            PrefWrapper.saveUser(getViewContext(), mUser);
                            FirebaseDatabase.getInstance().getReference(DBConstan.USERS).child(mUser.getId()).setValue(mUser);
                        } else {
                            Toast.makeText(getViewContext(), "Hết phiên đăng nhập", Toast.LENGTH_SHORT).show();
                        }
                    }


                });

    }
}
