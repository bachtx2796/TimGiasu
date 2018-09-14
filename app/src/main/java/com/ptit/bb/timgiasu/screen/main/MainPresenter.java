package com.ptit.bb.timgiasu.screen.main;

import android.content.Context;
import android.util.Log;

import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;
import com.gemvietnam.utils.StringUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.ptit.bb.timgiasu.Utils.DBConstan;
import com.ptit.bb.timgiasu.data.dto.GroupChatDTO;
import com.ptit.bb.timgiasu.data.dto.NotificationDataDTO;
import com.ptit.bb.timgiasu.data.dto.UserDTO;
import com.ptit.bb.timgiasu.prewrapper.PrefWrapper;
import com.ptit.bb.timgiasu.screen.chatdetail.ChatDetailPresenter;
import com.ptit.bb.timgiasu.screen.post.PostPresenter;

import static org.greenrobot.eventbus.EventBus.TAG;

/**
 * The Main Presenter
 */
public class MainPresenter extends Presenter<MainContract.View, MainContract.Interactor>
        implements MainContract.Presenter {

    private String grChatJson;
    private GroupChatDTO mGroupChatDTO;

    public MainPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public MainContract.View onCreateView() {
        return MainFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG, "Refreshed token: " + refreshedToken);
        UserDTO userDTO = PrefWrapper.getUser(getViewContext());
        if (userDTO != null) {
            FirebaseDatabase.getInstance().getReference(DBConstan.USERS).child(userDTO.getId()).child(DBConstan.DEVICE_TOKEN).setValue(refreshedToken);
        }
        if (!StringUtils.isEmpty(grChatJson)) {
            NotificationDataDTO notificationDataDTO = new Gson().fromJson(grChatJson, NotificationDataDTO.class);
            mGroupChatDTO = new GroupChatDTO(notificationDataDTO.getIdPost() + PrefWrapper.getUser(getViewContext()).getId() + notificationDataDTO.getIdUserSent(),
                    PrefWrapper.getUser(getViewContext()).getId(),
                    notificationDataDTO.getIdUserSent(),
                    notificationDataDTO.getIdPost(),
                    "");
            new ChatDetailPresenter(mContainerView).setGrChat(mGroupChatDTO).pushView();
        }
    }

    @Override
    public MainContract.Interactor onCreateInteractor() {
        return new MainInteractor(this);
    }

    @Override
    public void newPost() {
        new PostPresenter(mContainerView).pushView();
    }

    public MainPresenter setGrChatFromNoti(String grChatJson) {
        this.grChatJson = grChatJson;
        return this;
    }
}
