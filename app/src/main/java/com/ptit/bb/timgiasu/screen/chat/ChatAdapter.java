package com.ptit.bb.timgiasu.screen.chat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ptit.bb.timgiasu.R;
import com.ptit.bb.timgiasu.Utils.DBConstan;
import com.ptit.bb.timgiasu.Utils.DateTimeUtil;
import com.ptit.bb.timgiasu.data.dto.GroupChat;
import com.facebook.drawee.view.SimpleDraweeView;
import com.ptit.bb.timgiasu.data.dto.GroupChatDTO;
import com.ptit.bb.timgiasu.data.dto.MessageDTO;
import com.ptit.bb.timgiasu.data.dto.PostDTO;
import com.ptit.bb.timgiasu.prewrapper.PrefWrapper;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<GroupChatDTO> mGroupChats;
    private OnItemChatClickListener mOnItemChatClickListener;

    public void setmOnItemChatClickListener(OnItemChatClickListener mOnItemChatClickListener) {
        this.mOnItemChatClickListener = mOnItemChatClickListener;
    }

    public ChatAdapter(Context mContext, List<GroupChatDTO> mGroupChats) {
        this.mContext = mContext;
        this.mGroupChats = mGroupChats;
    }

    public class GroupChatHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_iv)
        SimpleDraweeView mItemIv;
        @BindView(R.id.item_title_tv)
        TextView mTitleTv;
        @BindView(R.id.itemUserAvatarIv)
        SimpleDraweeView mAvatarIv;
        @BindView(R.id.itemContainerLl)
        LinearLayout mItemLl;
        @BindView(R.id.item_last_message_tv)
        TextView mLastMsgTv;
        @BindView(R.id.item_last_message_time_tv)
        TextView mTimeTv;

        public GroupChatHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = View.inflate(mContext, R.layout.message_item, null);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(params);
        return new GroupChatHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final GroupChatHolder groupChatHolder = (GroupChatHolder) holder;
        groupChatHolder.mItemLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemChatClickListener.onItemChatClick(position);
            }
        });

        final GroupChatDTO groupChatDTO = mGroupChats.get(position);
        FirebaseDatabase.getInstance().getReference(DBConstan.CITIES).child(PrefWrapper.getUser(mContext).getCity()).child(DBConstan.POSTS).child(groupChatDTO.getIdPost()).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                PostDTO postDTO = dataSnapshot.getValue(PostDTO.class);
                if (postDTO.getUris() != null) {
                    groupChatHolder.mItemIv.setImageURI(postDTO.getUris().get(0));
                }
                groupChatHolder.mTitleTv.setText("Lớp: " + postDTO.getClasses().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(DBConstan.GR_CHAT).child(groupChatDTO.getId());
        Query lastQuery = databaseReference.orderByKey().limitToLast(1);
        lastQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    MessageDTO messageDTO = dataSnapshot1.getValue(MessageDTO.class);
                    groupChatHolder.mLastMsgTv.setText(messageDTO.getContent());
                    groupChatHolder.mTimeTv.setText(DateTimeUtil.longToTimeString(messageDTO.getTime()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return mGroupChats.size();
    }

    public interface OnItemChatClickListener {
        void onItemChatClick(int position);
    }
}
