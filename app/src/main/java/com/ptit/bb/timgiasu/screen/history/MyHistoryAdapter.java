package com.ptit.bb.timgiasu.screen.history;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ptit.bb.timgiasu.R;
import com.ptit.bb.timgiasu.data.dto.PostDTO;
import com.ptit.bb.timgiasu.screen.home.HomeAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyHistoryAdapter extends RecyclerView.Adapter{

    private Context mContext;
    private List<PostDTO> mPosts;
    private HomeAdapter.OnItemPostClickListener mOnItemPostClickListener;

    public MyHistoryAdapter(Context mContext, List<PostDTO> posts) {
        this.mContext = mContext;
        this.mPosts = posts;
    }

    public void setmOnItemPostClickListener(HomeAdapter.OnItemPostClickListener mOnItemPostClickListener) {
        this.mOnItemPostClickListener = mOnItemPostClickListener;
    }

    public class HistoryHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_iv)
        SimpleDraweeView mCoverIv;
        @BindView(R.id.name_tv)
        TextView mTitleTv;
        @BindView(R.id.price_tv)
        TextView mSalaryTv;
        @BindView(R.id.status_iv)
        TextView mStatusIv;

        public HistoryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(mContext, R.layout.item_my_history_details, null);
        return new HistoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        HistoryHolder historyHolder = (HistoryHolder) holder;
        PostDTO post = mPosts.get(position);
        historyHolder.mCoverIv.setImageURI(post.getUris().get(0));
        historyHolder.mTitleTv.setText("Lớp: " + post.getClasses().toString());
        historyHolder.mSalaryTv.setText(post.getSalary());
        historyHolder.mStatusIv.setText(post.getStatus());
        if (post.getStatus().equals("Đăng tuyển")){
            historyHolder.mStatusIv.setVisibility(View.VISIBLE);
            historyHolder.mStatusIv.setText("Đăng tuyển");
            historyHolder.mStatusIv.setBackground(ContextCompat.getDrawable(mContext,R.drawable.purchased));
        } else {
            historyHolder.mStatusIv.setVisibility(View.VISIBLE);
            historyHolder.mStatusIv.setText("Đã chốt");
            historyHolder.mStatusIv.setBackground(ContextCompat.getDrawable(mContext,R.drawable.sold));
        }
        historyHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemPostClickListener.onItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public interface OnItemPostClickListener {
        void onItemClick(int postion);
    }
}
