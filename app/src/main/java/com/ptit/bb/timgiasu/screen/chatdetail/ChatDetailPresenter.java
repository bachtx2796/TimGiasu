package com.ptit.bb.timgiasu.screen.chatdetail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;
import com.gemvietnam.utils.DialogUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ptit.bb.timgiasu.R;
import com.ptit.bb.timgiasu.Utils.DBConstan;
import com.ptit.bb.timgiasu.data.dto.GroupChatDTO;
import com.ptit.bb.timgiasu.data.dto.MessageDTO;
import com.ptit.bb.timgiasu.data.dto.NotificationDataDTO;
import com.ptit.bb.timgiasu.data.dto.PostDTO;
import com.ptit.bb.timgiasu.data.dto.PushNotificationDTO;
import com.ptit.bb.timgiasu.data.dto.UserDTO;
import com.ptit.bb.timgiasu.prewrapper.PrefWrapper;
import com.ptit.bb.timgiasu.pushnotification.MyFirebaseMessagingService;
import com.ptit.bb.timgiasu.screen.postdetail.PostDetailPresenter;
import com.ptit.bb.timgiasu.screen.tutorprofile.TutorProfilePresenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The ChatDetail Presenter
 */
public class ChatDetailPresenter extends Presenter<ChatDetailContract.View, ChatDetailContract.Interactor>
    implements ChatDetailContract.Presenter {

  private GroupChatDTO grChat;
  private PostDTO mPost;
  private UserDTO mUser; // current user
  private DatabaseReference mRef;
  private List<MessageDTO> mMessages;
  private ChatAdapter mAdapter;
  private UserDTO mOrther;
  FirebaseStorage storage;

  private ChildEventListener mChildEventListener = new ChildEventListener() {
    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
      MessageDTO msg = dataSnapshot.getValue(MessageDTO.class);
      if (!msg.getIdSender().equals(mUser.getId()) && !msg.isRead()) {
        dataSnapshot.getRef().child(DBConstan.READ).setValue(true);
      }
      mMessages.add(msg);
      mAdapter.notifyDataSetChanged();
      mView.updateListMsg(mMessages.size());
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
  };

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
    storage = FirebaseStorage.getInstance();
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
    mAdapter.setmOnActionChatListener(new ChatAdapter.OnActionChatListener() {
      @Override
      public void onAction(int position, String action) {
        if (action.equals(ChatAdapter.OnActionChatListener.VIEW_PROFILE)) {
          if (mOrther.isTutor()) {
            new TutorProfilePresenter(mContainerView).setTutor(mOrther).pushView();
          } else {
            Toast.makeText(getViewContext(), "Người dùng chưa đăng kí thông tin gia sư !", Toast.LENGTH_SHORT).show();
          }

        }
      }
    });
    mView.bindListMsg(mAdapter);
    mRef.addChildEventListener(mChildEventListener);
  }

  @Override
  public ChatDetailContract.Interactor onCreateInteractor() {
    return new ChatDetailInteractor(this);
  }

  public ChatDetailPresenter setGrChat(GroupChatDTO groupChatDTO) {
    this.grChat = groupChatDTO;
    return this;
  }

  private void getInfoOrther(String idUser) {
    mInteractor.getInfo(idUser, new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        mOrther = dataSnapshot.getValue(UserDTO.class);
        mView.bindUsername(mOrther.getName());
        getListMsg(mOrther.getAvatar());
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });
  }

  private void getPost() {
    // get info post
    mInteractor.getInfoPost(mUser.getCity(), grChat.getIdPost(), new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        mPost = dataSnapshot.getValue(PostDTO.class);
        mView.bindPost(mPost, grChat.getAction());
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });
  }

  @Override
  public void sendMsg(final String msg) {
    final MessageDTO messageDTO = new MessageDTO(msg, mUser.getId(), Calendar.getInstance().getTimeInMillis());
    mRef.push().setValue(messageDTO).addOnCompleteListener(new OnCompleteListener<Void>() {
      @Override
      public void onComplete(@NonNull Task<Void> task) {
        pushNotification(msg);
      }
    });
  }

  private void pushNotification(String msg) {
    PushNotificationDTO pushNotificationDTO = new PushNotificationDTO(mOrther.getDevicetoken(),
        new NotificationDataDTO(MyFirebaseMessagingService.MSG, mPost.getId(), mUser.getId(), mUser.getName() + ": " + msg));
    mInteractor.pushNotification(pushNotificationDTO, new Callback<Object>() {
      @Override
      public void onResponse(Call<Object> call, Response<Object> response) {

      }

      @Override
      public void onFailure(Call<Object> call, Throwable t) {

      }
    });
  }

  @Override
  public void removeListener() {
    mRef.removeEventListener(mChildEventListener);
  }

  @Override
  public void uploadImageWithFb(Intent data) {
    Uri filePath = data.getData();
    if (filePath != null) {
      DialogUtils.showProgressDialog(getViewContext());

      StorageReference ref = storage.getReference().child("images/" + UUID.randomUUID().toString());
      ref.putFile(filePath)
          .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
              DialogUtils.dismissProgressDialog();
              Uri uri = taskSnapshot.getDownloadUrl();
              sendMsg(uri.toString());
//                            iv.setImageURI(uri);
//                            if (iv.getId() == R.id.image1) {
//                                mListUri.set(0, uri.toString());
//                            } else if (iv.getId() == R.id.image2) {
//                                mListUri.set(1, uri.toString());
//                            }

            }
          })
          .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
              DialogUtils.dismissProgressDialog();
              Toast.makeText(getViewContext(), "Upload lỗi", Toast.LENGTH_SHORT).show();
            }
          });
    }
  }

  @Override
  public void viewPost() {
    new PostDetailPresenter(mContainerView).setPost(mPost).pushView();
  }

  @SuppressLint("MissingPermission")
  @Override
  public void callOwner() {
    String uri = "tel:" + mUser.getPhoneNo();
    Intent intent = new Intent(Intent.ACTION_CALL);
    intent.setData(Uri.parse(uri));
    getViewContext().startActivity(intent);
  }

  @Override
  public void pushNotiDecline() {
    DialogUtils.showProgressDialog(getViewContext());
    PushNotificationDTO pushNotificationDTO = new PushNotificationDTO(mOrther.getDevicetoken(), new NotificationDataDTO(MyFirebaseMessagingService.NOTI, mPost.getId(), mUser.getId(), mUser.getName() + " từ chối yêu cầu của bạn !"));
    mInteractor.pushNotification(pushNotificationDTO, new Callback<Object>() {

      @Override
      public void onResponse(Call<Object> call, Response<Object> response) {
        DialogUtils.dismissProgressDialog();
        Toast.makeText(getViewContext(), "Đã từ chối yêu cầu !", Toast.LENGTH_SHORT).show();
        removeChatByPost();
        back();
      }

      @Override
      public void onFailure(Call<Object> call, Throwable t) {
        DialogUtils.dismissProgressDialog();
      }
    });
  }

  private void removeChatByPost() {
    FirebaseDatabase.getInstance().getReference(DBConstan.USERS).child(mUser.getId()).child(DBConstan.GR_CHAT).child(grChat.getId()).removeValue();
    FirebaseDatabase.getInstance().getReference(DBConstan.USERS).child(mOrther.getId()).child(DBConstan.GR_CHAT).child(grChat.getId()).removeValue();
    FirebaseDatabase.getInstance().getReference(DBConstan.GR_CHAT).child(grChat.getId()).removeValue();
  }

  @Override
  public void pushNotiAcepted() {
    DialogUtils.showProgressDialog(getViewContext());
    PushNotificationDTO pushNotificationDTO = new PushNotificationDTO(mOrther.getDevicetoken(), new NotificationDataDTO(MyFirebaseMessagingService.NOTI, mPost.getId(), mUser.getId(), mUser.getName() + " chấp nhận yêu cầu của bạn !"));
    closePost();
    mInteractor.pushNotification(pushNotificationDTO, new Callback<Object>() {

      @Override
      public void onResponse(Call<Object> call, Response<Object> response) {
        FirebaseDatabase.getInstance().getReference(DBConstan.USERS).child(mOrther.getId()).child(DBConstan.RECEVIE_POST).addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
            DialogUtils.dismissProgressDialog();
            Toast.makeText(getViewContext(), "Đã chấp nhận yêu cầu !", Toast.LENGTH_SHORT).show();
            String postsJson = new Gson().toJson(dataSnapshot.getValue());
            List<PostDTO> receviepost = new Gson().fromJson(postsJson, new TypeToken<List<PostDTO>>() {
            }.getType());
            if (receviepost == null) receviepost = new ArrayList<>();
            mPost.setStatus("Đóng");
            receviepost.add(mPost);
            FirebaseDatabase.getInstance().getReference(DBConstan.USERS).child(mOrther.getId()).child(DBConstan.RECEVIE_POST).setValue(receviepost);
            back();
          }

          @Override
          public void onCancelled(DatabaseError databaseError) {

          }
        });

      }

      @Override
      public void onFailure(Call<Object> call, Throwable t) {
        DialogUtils.dismissProgressDialog();
      }
    });
  }

  private void closePost() {
    mView.showProgress();
    int position = mUser.getPosts().indexOf(mPost);
    mInteractor.closePost(mUser.getId(), position, mUser.getCity(), mPost.getId(), "Đóng");
  }

}
