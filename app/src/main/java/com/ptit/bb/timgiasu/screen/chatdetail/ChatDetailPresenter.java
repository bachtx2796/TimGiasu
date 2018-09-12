package com.ptit.bb.timgiasu.screen.chatdetail;

import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ptit.bb.timgiasu.Utils.DBConstan;
import com.ptit.bb.timgiasu.data.dto.GroupChatDTO;
import com.ptit.bb.timgiasu.data.dto.MessageDTO;
import com.ptit.bb.timgiasu.data.dto.PostDTO;
import com.ptit.bb.timgiasu.data.dto.UserDTO;
import com.ptit.bb.timgiasu.prewrapper.PrefWrapper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * The ChatDetail Presenter
 */
public class ChatDetailPresenter extends Presenter<ChatDetailContract.View, ChatDetailContract.Interactor>
        implements ChatDetailContract.Presenter {

    private String mId;
    private GroupChatDTO grChat;
    private PostDTO mPost;
    private UserDTO mUser; // current user
    private DatabaseReference mRef;
    private List<MessageDTO> mMessages;
    private ChatAdapter mAdapter;

    public ChatDetailPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public ChatDetailContract.View onCreateView() {
        return ChatDetailFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
        mRef = FirebaseDatabase.getInstance().getReference(DBConstan.GR_CHAT).child(grChat.getId());
        getData();
    }

    private void getData() {
        mUser = PrefWrapper.getUser(getViewContext());
        if (mUser.getId().equals(grChat.getIdOwner())) {
            // lay ten khach
            getInfoOrther(grChat.getIdClient());

        } else {
            //lay ten chu
            getInfoOrther(grChat.getIdOwner());
        }

        getPost();
    }

    private void getListMsg(String avatarOrther) {
        mMessages = new ArrayList<>();
        mAdapter = new ChatAdapter(getViewContext(), mMessages, avatarOrther);
        mView.bindListMsg(mAdapter);
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mMessages.add(dataSnapshot.getValue(MessageDTO.class));
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public ChatDetailContract.Interactor onCreateInteractor() {
        return new ChatDetailInteractor(this);
    }

    public ChatDetailPresenter setId(String id) {
        this.mId = id;
        return this;
    }

    public ChatDetailPresenter setGrChat(GroupChatDTO groupChatDTO) {
        this.grChat = groupChatDTO;
        return this;
    }

    private void getInfoOrther(String idUser) {
        FirebaseDatabase.getInstance().getReference(DBConstan.USERS).child(idUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDTO userDTO = dataSnapshot.getValue(UserDTO.class);
                mView.bindUsername(userDTO.getName());
                if (userDTO.getUris() != null) {
                    getListMsg(userDTO.getUris().get(0));
                } else {
                    getListMsg("");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getPost() {
        // get info post
        FirebaseDatabase.getInstance().getReference(DBConstan.CITIES).child(mUser.getCity()).child(DBConstan.POSTS).child(grChat.getIdPost()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mPost = dataSnapshot.getValue(PostDTO.class);
                mView.bindPost(mPost);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void sendMsg(String msg) {
        MessageDTO messageDTO = new MessageDTO(msg, mUser.getId(), Calendar.getInstance().getTimeInMillis());
        mRef.push().setValue(messageDTO);
    }
}
