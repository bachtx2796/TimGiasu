package com.ptit.bb.timgiasu.screen.chat;

import com.gemvietnam.utils.DialogUtils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ptit.bb.timgiasu.Utils.DBConstan;
import com.ptit.bb.timgiasu.data.dto.GroupChat;
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
        });
        mView.bindView(mChatAdapter);
        getData();
    }

    private void getData() {
        UserDTO userDTO = PrefWrapper.getUser(getViewContext());
        DialogUtils.showProgressDialog(getViewContext());
//        FirebaseDatabase.getInstance().getReference(DBConstan.USERS).child(userDTO.getId()).child(DBConstan.GR_CHAT).addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                mGroupChats.add(dataSnapshot.getValue(GroupChatDTO.class));
//                mChatAdapter.notifyDataSetChanged();
//                DialogUtils.dismissProgressDialog();
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                DialogUtils.dismissProgressDialog();
//            }
//        });

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
