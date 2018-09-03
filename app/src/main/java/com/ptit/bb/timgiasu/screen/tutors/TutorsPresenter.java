package com.ptit.bb.timgiasu.screen.tutors;

import com.gemvietnam.utils.DialogUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ptit.bb.timgiasu.Utils.DBConstan;
import com.ptit.bb.timgiasu.data.dto.TutorDTO;
import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;
import com.ptit.bb.timgiasu.data.dto.UserDTO;
import com.ptit.bb.timgiasu.prewrapper.PrefWrapper;
import com.ptit.bb.timgiasu.screen.tutorprofile.TutorProfilePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * The Tutors Presenter
 */
public class TutorsPresenter extends Presenter<TutorsContract.View, TutorsContract.Interactor>
        implements TutorsContract.Presenter {

    private List<UserDTO> mTutors;
    private TutorAdapter mTutorAdapter;

    public TutorsPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public TutorsContract.View onCreateView() {
        return TutorsFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
        mTutors = new ArrayList<>();
        mTutorAdapter = new TutorAdapter(getViewContext(), mTutors);
        mView.bindView(mTutorAdapter);
        getData();
    }

    private void getData() {
        DialogUtils.showProgressDialog(getViewContext());
        FirebaseDatabase.getInstance().getReference(DBConstan.CITIES).child(PrefWrapper.getUser(getViewContext()).getCity()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mTutors.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    mTutors.add(postSnapshot.getValue(UserDTO.class));
                }
                mTutorAdapter.setmOnItemTutorClickListenr(new TutorAdapter.OnItemTutorClickListenr() {
                    @Override
                    public void onTutorClick(int position) {
                        new TutorProfilePresenter(mContainerView).setTutor(mTutors.get(position)).pushView();
                    }
                });
                mTutorAdapter.notifyDataSetChanged();
                DialogUtils.dismissProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mTutorAdapter = new TutorAdapter(getViewContext(), mTutors);
        mView.bindView(mTutorAdapter);
    }

    @Override
    public TutorsContract.Interactor onCreateInteractor() {
        return new TutorsInteractor(this);
    }
}
