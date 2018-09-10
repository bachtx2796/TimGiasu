package com.ptit.bb.timgiasu.screen.home;

import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;
import com.gemvietnam.utils.DialogUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ptit.bb.timgiasu.Utils.DBConstan;
import com.ptit.bb.timgiasu.data.dto.ItemRecruit;
import com.ptit.bb.timgiasu.data.dto.PostDTO;
import com.ptit.bb.timgiasu.data.dto.UserDTO;
import com.ptit.bb.timgiasu.prewrapper.PrefWrapper;
import com.ptit.bb.timgiasu.screen.postdetail.PostDetailPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * The Home Presenter
 */
public class HomePresenter extends Presenter<HomeContract.View, HomeContract.Interactor>
        implements HomeContract.Presenter {

    private List<PostDTO> mPosts;
    private HomeAdapter mHomeAdapter;
    private UserDTO mUser;

    public HomePresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public HomeContract.View onCreateView() {
        return HomeFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
        mUser = PrefWrapper.getUser(getViewContext());

        mPosts = new ArrayList<>();
        getData();
    }

    private void getData() {
        DialogUtils.showProgressDialog(getViewContext());
        FirebaseDatabase.getInstance().getReference(DBConstan.CITIES).child(mUser.getCity()).child(DBConstan.POSTS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DialogUtils.dismissProgressDialog();
                mPosts.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    mPosts.add(postSnapshot.getValue(PostDTO.class));
                }
                mHomeAdapter = new HomeAdapter(getViewContext(), mPosts);
                mHomeAdapter.setmOnItemPostClickListener(new HomeAdapter.OnItemPostClickListener() {
                    @Override
                    public void onItemClick(int postion) {
                        new PostDetailPresenter(mContainerView).setPost(mPosts.get(postion)).pushView();
                    }
                });
                mView.bindView(mHomeAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public HomeContract.Interactor onCreateInteractor() {
        return new HomeInteractor(this);
    }

    @Override
    public void refreshData() {
        getData();
    }
}
