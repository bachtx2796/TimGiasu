package com.ptit.bb.timgiasu.screen.chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ptit.bb.timgiasu.R;
import com.ptit.bb.timgiasu.data.dto.GroupChat;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<GroupChat> mGroupChats;
    private OnItemChatClickListener mOnItemChatClickListener;

    public void setmOnItemChatClickListener(OnItemChatClickListener mOnItemChatClickListener) {
        this.mOnItemChatClickListener = mOnItemChatClickListener;
    }

    public ChatAdapter(Context mContext, List<GroupChat> mGroupChats) {
        this.mContext = mContext;
        this.mGroupChats = mGroupChats;
    }

    public class GroupChatHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.itemUserAvatarIv)
        SimpleDraweeView mAvatarIv;
        @BindView(R.id.itemContainerLl)
        LinearLayout mItemLl;

        public GroupChatHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GroupChatHolder groupChatHolder = (GroupChatHolder) holder;
        groupChatHolder.mItemLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemChatClickListener.onItemChatClick("");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mGroupChats.size();
    }

    public interface OnItemChatClickListener{
        void onItemChatClick(String id);
    }
}
