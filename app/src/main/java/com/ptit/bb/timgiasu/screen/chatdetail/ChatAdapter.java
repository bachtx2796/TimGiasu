package com.ptit.bb.timgiasu.screen.chatdetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ptit.bb.timgiasu.R;
import com.ptit.bb.timgiasu.Utils.DateTimeUtil;
import com.ptit.bb.timgiasu.data.dto.MessageDTO;
import com.ptit.bb.timgiasu.prewrapper.PrefWrapper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<MessageDTO> messages;
    private String uriOrther;

    private static final int TYPE_CURENT_USER = 101;
    private static final int TYPE_ORTHER_USER = 102;

    public ChatAdapter(Context mContext, List<MessageDTO> messages, String uriOrther) {
        this.mContext = mContext;
        this.messages = messages;
        this.uriOrther = uriOrther;
    }

    public class LeftChatViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.user_left_avatar_iv)
        SimpleDraweeView mAvatarIv;
        @BindView(R.id.message_left_tv)
        TextView mMessageTv;
        @BindView(R.id.message_left_time_tv)
        TextView mTimeTv;

        public LeftChatViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class RightChatViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.message_tv)
        TextView mMessageTv;
        @BindView(R.id.message_time_tv)
        TextView mTimeTv;

        public RightChatViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        MessageDTO msg = messages.get(position);
        if (msg.getIdSender().equals(PrefWrapper.getUser(mContext).getId())) {
            return TYPE_CURENT_USER;
        } else {
            return TYPE_ORTHER_USER;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (viewType == TYPE_CURENT_USER) {
            itemView = View.inflate(mContext, R.layout.item_right_chat, null);
            itemView.setLayoutParams(params);
            return new RightChatViewHolder(itemView);
        } else if (viewType == TYPE_ORTHER_USER) {
            itemView = View.inflate(mContext, R.layout.item_left_chat, null);
            itemView.setLayoutParams(params);
            return new LeftChatViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MessageDTO messageDTO = messages.get(position);
        if (holder instanceof LeftChatViewHolder) {
            LeftChatViewHolder leftChatViewHolder = (LeftChatViewHolder) holder;
            leftChatViewHolder.mAvatarIv.setImageURI(uriOrther);
            leftChatViewHolder.mMessageTv.setText(messageDTO.getContent());
            leftChatViewHolder.mTimeTv.setText(DateTimeUtil.longToTimeString(messageDTO.getTime()));
        } else if (holder instanceof RightChatViewHolder) {
            RightChatViewHolder rightChatViewHolder = (RightChatViewHolder) holder;
            rightChatViewHolder.mMessageTv.setText(messageDTO.getContent());
            rightChatViewHolder.mTimeTv.setText(DateTimeUtil.longToTimeString(messageDTO.getTime()));
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
