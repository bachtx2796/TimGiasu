package com.ptit.bb.timgiasu.screen.register;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;
import com.gemvietnam.utils.DialogUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ptit.bb.timgiasu.Utils.DBConstan;
import com.ptit.bb.timgiasu.data.dto.UserDTO;
import com.ptit.bb.timgiasu.prewrapper.PrefWrapper;
import com.ptit.bb.timgiasu.screen.map.MyMapPresenter;
import com.ptit.bb.timgiasu.screen.verifyphoneno.VerifyPhoneNoPresenter;

import java.util.concurrent.TimeUnit;

/**
 * The Register Presenter
 */
public class RegisterPresenter extends Presenter<RegisterContract.View, RegisterContract.Interactor>
        implements RegisterContract.Presenter {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private long sizeUsers = 0;
    private DatabaseReference mDatabase;

    public RegisterPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public RegisterContract.View onCreateView() {
        return RegisterFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
        mDatabase = FirebaseDatabase.getInstance().getReference(DBConstan.CITIES);
    }

    @Override
    public RegisterContract.Interactor onCreateInteractor() {
        return new RegisterInteractor(this);
    }

    @Override
    public void signup(final String fullname, final String email, final String phoneNo, final String gender, final String pass, String confirmpass, final String dob, final String city, final String address) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber("+84" + phoneNo, 60, TimeUnit.SECONDS, getViewContext(), new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(getViewContext(), "Số điện thoại không tồn tại", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                new VerifyPhoneNoPresenter(mContainerView)
                        .setPhoneNo("+84" + phoneNo)
                        .setVerificationID(verificationId)
                        .setOnVerifyPhoneNumberListener(new VerifyPhoneNoPresenter.OnVerifyPhoneNumberListener() {
                            @Override
                            public void onVerifyPhoneNumberSuccessful() {
                                mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information

                                            Log.d("@@@@", "createUserWithEmail:success");
                                            // save user into db
                                            sizeUsers++;
                                            addSizeUsers(city, sizeUsers);
                                            saveInfoUser(task.getResult().getUser().getUid(), fullname, email, phoneNo, gender, dob, city, address);
                                            DialogUtils.dismissProgressDialog();
                                            back();
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Log.w("@@@@", "createUserWithEmail:failure", task.getException());
                                            Toast.makeText(getViewContext(), "Đăng kí không thành công",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }

                            @Override
                            public void onVerifyPhoneNumberFailed() {

                            }
                        })
                        .pushView();
            }
        });


    }

    @Override
    public void showMap() {
        MyMapPresenter presenter = new MyMapPresenter(mContainerView);
        presenter.setmOnLocationSelectedListener(new MyMapPresenter.OnLocationSelectedListener() {
            @Override
            public void onItemSelected(String location) {
                mView.setLocation(location);
            }
        });
        presenter.pushView();
    }

    @Override
    public void getSizeUserFromDB(String city) {
        mDatabase.child(city).child(DBConstan.SIZE_USERS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    sizeUsers = 0;
                } else {
                    sizeUsers = (long) dataSnapshot.getValue();
                }

                Log.e("@@@@@", sizeUsers + "");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addSizeUsers(String city, long size) {
        mDatabase.child(city).child(DBConstan.SIZE_USERS).setValue(size);
    }

    private void saveInfoUser(String id, String fullname, String email, String phoneNo, String gender, String dob, String city, String address) {
        UserDTO userDTO = new UserDTO(id, fullname, email, city, phoneNo, gender, dob, address);
        mDatabase.child(city).child(DBConstan.USERS).child(String.valueOf(sizeUsers - 1)).setValue(userDTO);
        mDatabase.child(DBConstan.USERS).child(id).setValue(userDTO);
    }
}
