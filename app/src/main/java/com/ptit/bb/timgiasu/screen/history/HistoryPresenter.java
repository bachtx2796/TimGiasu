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
import com.ptit.bb.timgiasu.screen.postdetail.PostDetailPresenter;

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
  private MyHistoryAdapter mPostAdapter;
  private MyHistoryAdapter mReceviePostAdapter;

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
    mPostAdapter = new MyHistoryAdapter(getViewContext(), mPosts);
    mPostAdapter.setmOnItemPostClickListener(new HomeAdapter.OnItemPostClickListener() {
      @Override
      public void onItemClick(int postion) {
        if (mPosts.size() > 0){
          showPostDeatail(mPosts.get(postion));
        }
      }
    });

    mReceviePost = new ArrayList<>();
    mReceviePostAdapter = new MyHistoryAdapter(getViewContext(), mReceviePost);
    mReceviePostAdapter.setmOnItemPostClickListener(new HomeAdapter.OnItemPostClickListener() {
      @Override
      public void onItemClick(int postion) {
        if (mReceviePost.size() > 0){
          showPostDeatail(mReceviePost.get(postion));
        }
      }
    });

    mView.bindPost(mPostAdapter, mReceviePostAdapter);
    userDTO = PrefWrapper.getUser(getViewContext());
    getHistory();
  }

  private void showPostDeatail(PostDTO post) {
    new PostDetailPresenter(mContainerView).setPost(post).pushView();
  }

  private void getHistory() {
    final DatabaseReference ref = FirebaseDatabase.getInstance().getReference(DBConstan.USERS).child(userDTO.getId());
    DialogUtils.showProgressDialog(getViewContext());
    ref.child(DBConstan.MY_POST).addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        String postsJson = new Gson().toJson(dataSnapshot.getValue());
        postsJson = postsJson.replaceAll("null,", "");
        List<PostDTO> tmp = new Gson().fromJson(postsJson, new TypeToken<List<PostDTO>>() {
        }.getType());
        mPosts.clear();
        if (tmp != null) {
          mPosts.addAll(tmp);
        }
        Log.e("@@@@", mPosts.toString());
        ref.child(DBConstan.RECEVIE_POST).addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
            DialogUtils.dismissProgressDialog();
            String rpostsJson = new Gson().toJson(dataSnapshot.getValue());
            List<PostDTO> tmp = new Gson().fromJson(rpostsJson, new TypeToken<List<PostDTO>>() {
            }.getType());
            mReceviePost.clear();
            if (tmp != null) {
              mReceviePost.addAll(tmp);
            }
            mPostAdapter.notifyDataSetChanged();
            mReceviePostAdapter.notifyDataSetChanged();
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
