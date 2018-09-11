package com.ptit.bb.timgiasu.screen.postdetail;

import android.util.Log;
import android.widget.Toast;

import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;
import com.gemvietnam.utils.DialogUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ptit.bb.timgiasu.Utils.DBConstan;
import com.ptit.bb.timgiasu.data.dto.NotificationDataDTO;
import com.ptit.bb.timgiasu.data.dto.PostDTO;
import com.ptit.bb.timgiasu.data.dto.PushNotificationDTO;
import com.ptit.bb.timgiasu.prewrapper.PrefWrapper;
import com.ptit.bb.timgiasu.screen.editpost.EditPostPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The PostDetail Presenter
 */
public class PostDetailPresenter extends Presenter<PostDetailContract.View, PostDetailContract.Interactor>
        implements PostDetailContract.Presenter {

    private PostDTO mPost;

    public PostDetailPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public PostDetailContract.View onCreateView() {
        return PostDetailFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
        mView.bindView(mPost);
    }

    @Override
    public PostDetailContract.Interactor onCreateInteractor() {
        return new PostDetailInteractor(this);
    }

    public PostDetailPresenter setPost(PostDTO postDTO) {
        mPost = postDTO;
        return this;
    }

    @Override
    public void editPost() {
        new EditPostPresenter(mContainerView)
                .setPost(mPost)
                .pushView();
    }


    @Override
    public void sentRequest() {
        DialogUtils.showProgressDialog(getViewContext());
        FirebaseDatabase.getInstance().getReference(DBConstan.USERS).child(mPost.getIdUser()).child(DBConstan.DEVICE_TOKEN).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String token = dataSnapshot.getValue(String.class);
                PushNotificationDTO pushNotificationDTO = new PushNotificationDTO(token, new NotificationDataDTO(PrefWrapper.getUser(getViewContext()).getId(), "Nhận nè"));
                mInteractor.sendRequest(pushNotificationDTO, new Callback<Object>() {

                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        DialogUtils.dismissProgressDialog();
                        if (response.isSuccessful()) {
                            mView.sendRequestSuccess();
                            Toast.makeText(getViewContext(), "Gửi yêu cầu nhận lớp thành công !", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getViewContext(), "Có lỗi xảy ra !", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        Toast.makeText(getViewContext(), "Có lỗi xảy ra !", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
