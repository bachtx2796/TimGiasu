package com.ptit.bb.timgiasu.screen.postdetail;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;
import com.gemvietnam.utils.DialogUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ptit.bb.timgiasu.Utils.DBConstan;
import com.ptit.bb.timgiasu.data.dto.GroupChatDTO;
import com.ptit.bb.timgiasu.data.dto.NotificationDataDTO;
import com.ptit.bb.timgiasu.data.dto.PostDTO;
import com.ptit.bb.timgiasu.data.dto.PushNotificationDTO;
import com.ptit.bb.timgiasu.data.dto.UserDTO;
import com.ptit.bb.timgiasu.prewrapper.PrefWrapper;
import com.ptit.bb.timgiasu.pushnotification.MyFirebaseMessagingService;
import com.ptit.bb.timgiasu.screen.chatdetail.ChatDetailPresenter;
import com.ptit.bb.timgiasu.screen.editpost.EditPostPresenter;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The PostDetail Presenter
 */
public class PostDetailPresenter extends Presenter<PostDetailContract.View, PostDetailContract.Interactor>
        implements PostDetailContract.Presenter {

    private PostDTO mPost;
    private UserDTO mUser;

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
        mUser = PrefWrapper.getUser(getViewContext());
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
                PushNotificationDTO pushNotificationDTO = new PushNotificationDTO(token, new NotificationDataDTO(MyFirebaseMessagingService.REQUEST, mPost.getId(), mUser.getId(), "Nhận nè"));
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

    @Override
    public void createGrChat() {
        final String key = mPost.getId() + mPost.getIdUser() + PrefWrapper.getUser(getViewContext()).getId(); // tao key gr
        final GroupChatDTO groupChatDTO = new GroupChatDTO(key, mPost.getIdUser(), PrefWrapper.getUser(getViewContext()).getId(), mPost.getId(), "");

        // tao gr chat cho ng ban' va mua
        DialogUtils.showProgressDialog(getViewContext());
        FirebaseDatabase.getInstance().getReference(DBConstan.USERS).child(mPost.getIdUser()).child(DBConstan.GR_CHAT).child(key).setValue(groupChatDTO).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                FirebaseDatabase.getInstance().getReference(DBConstan.USERS).child(PrefWrapper.getUser(getViewContext()).getId()).child(DBConstan.GR_CHAT).child(key).setValue(groupChatDTO).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        DialogUtils.dismissProgressDialog();
                        new ChatDetailPresenter(mContainerView).setGrChat(groupChatDTO).pushView();
                    }
                });
            }
        });

    }
}
