package com.ptit.bb.timgiasu.screen.post;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;
import com.gemvietnam.utils.DialogUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.ptit.bb.timgiasu.Utils.AppUtils;
import com.ptit.bb.timgiasu.Utils.DBConstan;
import com.ptit.bb.timgiasu.data.dto.PostDTO;
import com.ptit.bb.timgiasu.data.dto.UserDTO;
import com.ptit.bb.timgiasu.prewrapper.PrefWrapper;
import com.ptit.bb.timgiasu.screen.map.MyMapPresenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * The Post Presenter
 */
public class PostPresenter extends Presenter<PostContract.View, PostContract.Interactor>
        implements PostContract.Presenter {

    public PostPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public PostContract.View onCreateView() {
        return PostFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public PostContract.Interactor onCreateInteractor() {
        return new PostInteractor(this);
    }

    @Override
    public void newPost(String address, List<String> mClasses, List<String> mSubjects, String time, String salary, List<String> mListUri) {
        final UserDTO userDTO = PrefWrapper.getUser(getViewContext());
        String idPost = FirebaseDatabase.getInstance().getReference(DBConstan.CITIES).child(userDTO.getCity()).child(DBConstan.POSTS).push().getKey(); // gen id post
        final PostDTO postDTO = new PostDTO(idPost, userDTO.getId(), mListUri, address, mClasses, mSubjects, salary, AppUtils.DANG_TUYEN, Calendar.getInstance().getTimeInMillis(), time);
        DialogUtils.showProgressDialog(getViewContext());
        FirebaseDatabase.getInstance().getReference(DBConstan.CITIES).child(userDTO.getCity()).child(DBConstan.POSTS).child(idPost).setValue(postDTO).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                List<PostDTO> posts = userDTO.getPosts();
                if (posts == null) posts = new ArrayList<>();
                posts.add(postDTO);
                FirebaseDatabase.getInstance().getReference(DBConstan.USERS).child(userDTO.getId()).child(DBConstan.MY_POST).setValue(posts);
                userDTO.setPosts(posts);
                PrefWrapper.saveUser(getViewContext(),userDTO);
                DialogUtils.dismissProgressDialog();
                Toast.makeText(getViewContext(), "Đăng tuyển thành công", Toast.LENGTH_SHORT).show();
                back();
            }
        });
    }

    @Override
    public void pickAddress() {
        MyMapPresenter presenter = new MyMapPresenter(mContainerView);
        presenter.setmOnLocationSelectedListener(new MyMapPresenter.OnLocationSelectedListener() {
            @Override
            public void onItemSelected(String location) {
                mView.setLocation(location);
            }
        });
        presenter.pushView();
    }
}
