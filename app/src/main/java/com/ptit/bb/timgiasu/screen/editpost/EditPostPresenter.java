package com.ptit.bb.timgiasu.screen.editpost;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;
import com.gemvietnam.utils.DialogUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.ptit.bb.timgiasu.Utils.DBConstan;
import com.ptit.bb.timgiasu.data.dto.Coord;
import com.ptit.bb.timgiasu.data.dto.PostDTO;
import com.ptit.bb.timgiasu.data.dto.UserDTO;
import com.ptit.bb.timgiasu.prewrapper.PrefWrapper;
import com.ptit.bb.timgiasu.screen.map.MyMapPresenter;

import java.util.Calendar;
import java.util.List;

/**
 * The EditPost Presenter
 */
public class EditPostPresenter extends Presenter<EditPostContract.View, EditPostContract.Interactor>
        implements EditPostContract.Presenter {

    private PostDTO mPost;

    public EditPostPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public EditPostContract.View onCreateView() {
        return EditPostFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
        mView.bindPost(mPost);
    }

    @Override
    public EditPostContract.Interactor onCreateInteractor() {
        return new EditPostInteractor(this);
    }

    public EditPostPresenter setPost(PostDTO post) {
        this.mPost = post;
        return this;
    }

    @Override
    public void pickAddress() {
        MyMapPresenter presenter = new MyMapPresenter(mContainerView);
        presenter.setmOnLocationSelectedListener(new MyMapPresenter.OnLocationSelectedListener() {
            @Override
            public void onItemSelected(String location, Coord coord) {
                mView.setLocation(location);
            }
        });
        presenter.pushView();
    }

    @Override
    public void savePost(String address, List<String> mClasses, List<String> mSubjects, String time, String salary, List<String> mListUri) {
        mPost.setAddress(address);
        mPost.setClasses(mClasses);
        mPost.setSubjects(mSubjects);
        mPost.setTime(time);
        mPost.setSalary(salary);
        mPost.setUris(mListUri);
        mPost.setTimecreate(Calendar.getInstance().getTimeInMillis());

        UserDTO userDTO = PrefWrapper.getUser(getViewContext());
        DialogUtils.showProgressDialog(getViewContext());

        mInteractor.savePost(userDTO.getCity(), mPost.getId(), mPost, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                DialogUtils.dismissProgressDialog();
                Toast.makeText(getViewContext(), "Sửa thông tin bài đăng thành công !", Toast.LENGTH_SHORT).show();
                back();
                back();
            }
        });
    }
}
