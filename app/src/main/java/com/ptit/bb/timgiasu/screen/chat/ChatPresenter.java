package com.ptit.bb.timgiasu.screen.chat;

import com.gemvietnam.utils.DialogUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ptit.bb.timgiasu.Utils.DBConstan;
import com.ptit.bb.timgiasu.data.dto.GroupChatDTO;
import com.ptit.bb.timgiasu.data.dto.UserDTO;
import com.ptit.bb.timgiasu.prewrapper.PrefWrapper;
import com.ptit.bb.timgiasu.screen.chatdetail.ChatDetailPresenter;
import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;

import java.util.ArrayList;
import java.util.List;

/**
 * The Chat Presenter
 */
public class ChatPresenter extends Presenter<ChatContract.View, ChatContract.Interactor>
        implements ChatContract.Presenter {

    private List<GroupChatDTO> mGroupChats;
    private ChatAdapter mChatAdapter;

    public ChatPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public ChatContract.View onCreateView() {
        return ChatFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
        mGroupChats = new ArrayList<>();
        mChatAdapter = new ChatAdapter(getViewContext(), mGroupChats);
        mChatAdapter.setmOnItemChatClickListener(new ChatAdapter.OnItemChatClickListener() {
            @Override
            public void onItemChatClick(int position) {
                new ChatDetailPresenter(mContainerView).setGrChat(mGroupChats.get(position)).pushView();
            }

            @Override
            public void onDeleteClick(int position) {
                deleteGrChat(mGroupChats.get(position));
                mGroupChats.remove(position);
                mChatAdapter.notifyDataSetChanged();
            }
        });
        mView.bindView(mChatAdapter);
        getData();
    }

    private void deleteGrChat(GroupChatDTO groupChatDTO) {
        FirebaseDatabase.getInstance().getReference(DBConstan.GR_CHAT).child(groupChatDTO.getId()).removeValue();
        FirebaseDatabase.getInstance().getReference(DBConstan.USERS).child(groupChatDTO.getIdOwner()).child(DBConstan.GR_CHAT).child(groupChatDTO.getId()).removeValue();
        FirebaseDatabase.getInstance().getReference(DBConstan.USERS).child(groupChatDTO.getIdClient()).child(DBConstan.GR_CHAT).child(groupChatDTO.getId()).removeValue();
    }

    private void getData() {
        UserDTO userDTO = PrefWrapper.getUser(getViewContext());
        DialogUtils.showProgressDialog(getViewContext());

        FirebaseDatabase.getInstance().getReference(DBConstan.USERS).child(userDTO.getId()).child(DBConstan.GR_CHAT).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    mGroupChats.add(dataSnapshot1.getValue(GroupChatDTO.class));
                }
                mChatAdapter.notifyDataSetChanged();
                DialogUtils.dismissProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public ChatContract.Interactor onCreateInteractor() {
        return new ChatInteractor(this);
    }
}
