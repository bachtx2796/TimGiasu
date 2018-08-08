package com.example.bb.timgiasu.screen.chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.bb.timgiasu.R;
import com.example.bb.timgiasu.data.dto.GroupChat;

import java.util.List;

import butterknife.ButterKnife;

public class ChatAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<GroupChat> mGroupChats;

    public ChatAdapter(Context mContext, List<GroupChat> mGroupChats) {
        this.mContext = mContext;
        this.mGroupChats = mGroupChats;
    }

    public class GroupChatHolder extends RecyclerView.ViewHolder {

        public GroupChatHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this.itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = View.inflate(mContext, R.layout.message_item, null);
        ViewGroup.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(params);
        return new GroupChatHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GroupChatHolder groupChatHolder = (GroupChatHolder) holder;
    }

    @Override
    public int getItemCount() {
        return mGroupChats.size();
    }
}
