package com.ptit.bb.timgiasu.screen.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ptit.bb.timgiasu.R;
import com.ptit.bb.timgiasu.data.dto.ItemRecruit;
import com.ptit.bb.timgiasu.data.dto.PostDTO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<PostDTO> mPosts;
    private OnItemPostClickListener mOnItemPostClickListener;

    public HomeAdapter(Context mContext, List<PostDTO> posts) {
        this.mContext = mContext;
        this.mPosts = posts;
    }

    public void setmOnItemPostClickListener(OnItemPostClickListener mOnItemPostClickListener) {
        this.mOnItemPostClickListener = mOnItemPostClickListener;
    }

    public class HomeHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.itemCoverIv)
        SimpleDraweeView mCoverIv;
        @BindView(R.id.itemTitleTv)
        TextView mTitleTv;
        @BindView(R.id.itemSalaryTv)
        TextView mSalaryTv;

        public HomeHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(mContext, R.layout.item_recruit, null);
        return new HomeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        HomeHolder homeHolder = (HomeHolder) holder;

        homeHolder.itemView.setOnClickListener(new View.OnClickListener() {
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
