package com.ptit.bb.timgiasu.screen.profile;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;
import com.gemvietnam.utils.ActivityUtils;
import com.gemvietnam.utils.DialogUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.ptit.bb.timgiasu.Utils.DBConstan;
import com.ptit.bb.timgiasu.data.dto.UserDTO;
import com.ptit.bb.timgiasu.prewrapper.PrefWrapper;
import com.ptit.bb.timgiasu.screen.changepassword.ChangePasswordPresenter;
import com.ptit.bb.timgiasu.screen.login.LoginActivity;

import java.util.List;

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
    public void saveUser(final UserDTO mUser, boolean setEmail, final boolean setCity) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DialogUtils.showProgressDialog(getViewContext());
        if (setEmail) {
            user.updateEmail(mUser.getEmail())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "User email address updated.");
                                if (mUser.isTutor() && setCity){
                                    FirebaseDatabase.getInstance().getReference(DBConstan.CITIES).child(PrefWrapper.getUser(getViewContext()).getCity()).child(DBConstan.USERS).child(mUser.getId()).removeValue();
                                    FirebaseDatabase.getInstance().getReference(DBConstan.CITIES).child(mUser.getCity()).child(DBConstan.USERS).child(mUser.getId()).setValue(mUser);
                                }
                                FirebaseDatabase.getInstance().getReference(DBConstan.USERS).child(mUser.getId()).setValue(mUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        PrefWrapper.saveUser(getViewContext(), mUser);
                                        Toast.makeText(getViewContext(), "Lưu thông tin thành công", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                DialogUtils.dismissProgressDialog();
                            } else {
                                DialogUtils.dismissProgressDialog();
                                Toast.makeText(getViewContext(), "Hết phiên đăng nhập", Toast.LENGTH_SHORT).show();
                            }
                        }


                    });
        } else {
            if (mUser.isTutor() && setCity){
                FirebaseDatabase.getInstance().getReference(DBConstan.CITIES).child(PrefWrapper.getUser(getViewContext()).getCity()).child(DBConstan.USERS).child(mUser.getId()).removeValue();
                FirebaseDatabase.getInstance().getReference(DBConstan.CITIES).child(mUser.getCity()).child(mUser.getId()).child(DBConstan.USERS).setValue(mUser);
            }
            FirebaseDatabase.getInstance().getReference(DBConstan.USERS).child(mUser.getId()).setValue(mUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    PrefWrapper.saveUser(getViewContext(), mUser);
                    Toast.makeText(getViewContext(), "Lưu thông tin thành công", Toast.LENGTH_SHORT).show();
                    DialogUtils.dismissProgressDialog();
                }
            });
        }

    }

    @Override
    public void changePassword() {
        new ChangePasswordPresenter(mContainerView).pushView();
    }

    @Override
    public void saveTutor(List<String> mClasses, List<String> mSubjects, String time, String salary, List<String> mListUri) {
        final UserDTO user = PrefWrapper.getUser(getViewContext());
        user.setClasses(mClasses);
        user.setSubjects(mSubjects);
        user.setTime(time);
        user.setSalary(Long.parseLong(salary));
        user.setUris(mListUri);
        user.setTutor(true);
        DialogUtils.showProgressDialog(getViewContext());
        FirebaseDatabase.getInstance().getReference(DBConstan.USERS).child(user.getId()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                PrefWrapper.saveUser(getViewContext(), user);
                DialogUtils.dismissProgressDialog();
                FirebaseDatabase.getInstance().getReference(DBConstan.CITIES).child(user.getCity()).child(DBConstan.USERS).child(user.getId()).setValue(user); // luu list user là tutor vao city
                Toast.makeText(getViewContext(), "Lưu thông tin thành công", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                DialogUtils.dismissProgressDialog();
                Toast.makeText(getViewContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
