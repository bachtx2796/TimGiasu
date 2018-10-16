package com.ptit.bb.timgiasu.screen.history;

import android.util.Log;

import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;
import com.gemvietnam.utils.DialogUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ptit.bb.timgiasu.Utils.DBConstan;
import com.ptit.bb.timgiasu.data.dto.PostDTO;
import com.ptit.bb.timgiasu.data.dto.UserDTO;
import com.ptit.bb.timgiasu.prewrapper.PrefWrapper;
import com.ptit.bb.timgiasu.screen.home.HomeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * The History Presenter
 */
public class HistoryPresenter extends Presenter<HistoryContract.View, HistoryContract.Interactor>
        implements HistoryContract.Presenter {

    private UserDTO userDTO;
    private List<PostDTO> mPosts;
    private List<PostDTO> mReceviePost;
    private HomeAdapter mPostAdapter;
    private HomeAdapter mReceviePostAdapter;

    public HistoryPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public HistoryContract.View onCreateView() {
        return HistoryFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
        mPosts = new ArrayList<>();
        mPostAdapter = new HomeAdapter(getViewContext(), mPosts);
        mReceviePost = new ArrayList<>();
        mReceviePostAdapter = new HomeAdapter(getViewContext(), mReceviePost);
        mView.bindPost(mPostAdapter,mReceviePostAdapter);
        userDTO = PrefWrapper.getUser(getViewContext());
        getHistory();
    }

    private void getHistory() {
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference(DBConstan.USERS).child(userDTO.getId());
        DialogUtils.showProgressDialog(getViewContext());
        ref.child(DBConstan.MY_POST).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String postsJson = new Gson().toJson(dataSnapshot.getValue());
                List<PostDTO> tmp = new Gson().fromJson(postsJson, new TypeToken<List<PostDTO>>() {
                }.getType());
                mPosts.clear();
                mPosts.addAll(tmp);
                Log.e("@@@@", mPosts.toString());
                ref.child(DBConstan.RECEVIE_POST).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        DialogUtils.dismissProgressDialog();
                        String posts = new Gson().toJson(dataSnapshot.getValue());
                        List<PostDTO> tmp = new Gson().fromJson(postsJson,new TypeToken<List<PostDTO>>(){}.getType());

                        mPostAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public HistoryContract.Interactor onCreateInteractor() {
        return new HistoryInteractor(this);
    }
}
