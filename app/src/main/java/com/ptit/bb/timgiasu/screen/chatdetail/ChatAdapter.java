package com.ptit.bb.timgiasu.screen.chatdetail;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.firebase.database.DataSnapshot;
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
    private static final int TYPE_IMAGE_CURENT_USER = 103;
    private static final int TYPE_IMAGE_ORTHER_USER = 104;

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

    public class LeftChatImageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.other_user_file_image_avatar_iv)
        SimpleDraweeView mAvatarIv;
        @BindView(R.id.other_image_group_chat_file_thumbnail)
        SimpleDraweeView mImageIv;
        @BindView(R.id.other_text_group_chat_time)
        TextView mTimeTv;

        public LeftChatImageViewHolder(View itemView) {
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

    public class RightChatImageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_group_chat_file_thumbnail)
        SimpleDraweeView mImageIv;
        @BindView(R.id.text_group_chat_time)
        TextView mTimeTv;

        public RightChatImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        MessageDTO msg = messages.get(position);
        if (msg.getIdSender().equals(PrefWrapper.getUser(mContext).getId())) {
            if (msg.getContent().contains("https://firebasestorage.googleapis.com/") && UriUtil.isNetworkUri(Uri.parse(msg.getContent()))) {
                return TYPE_IMAGE_CURENT_USER;
            }
            return TYPE_CURENT_USER;
        } else {
            if (msg.getContent().contains("https://firebasestorage.googleapis.com/") && UriUtil.isNetworkUri(Uri.parse(msg.getContent()))) {
                return TYPE_IMAGE_ORTHER_USER;
            }
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
        } else if (viewType == TYPE_IMAGE_CURENT_USER) {
            itemView = View.inflate(mContext, R.layout.list_item_group_chat_file_image_me, null);
            itemView.setLayoutParams(params);
            return new RightChatImageViewHolder(itemView);
        } else if (viewType == TYPE_IMAGE_ORTHER_USER) {
            itemView = View.inflate(mContext, R.layout.list_item_group_chat_file_image_other, null);
            itemView.setLayoutParams(params);
            return new LeftChatImageViewHolder(itemView);
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
        } else if (holder instanceof LeftChatImageViewHolder) {
            LeftChatImageViewHolder leftChatImageViewHolder = (LeftChatImageViewHolder) holder;
            leftChatImageViewHolder.mAvatarIv.setImageURI(uriOrther);
            leftChatImageViewHolder.mImageIv.setImageURI(messageDTO.getContent());
            leftChatImageViewHolder.mTimeTv.setText(DateTimeUtil.longToTimeString(messageDTO.getTime()));
        } else if (holder instanceof RightChatImageViewHolder) {
            RightChatImageViewHolder rightChatImageViewHolder = (RightChatImageViewHolder) holder;
            rightChatImageViewHolder.mImageIv.setImageURI(messageDTO.getContent());
            rightChatImageViewHolder.mTimeTv.setText(DateTimeUtil.longToTimeString(messageDTO.getTime()));
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
