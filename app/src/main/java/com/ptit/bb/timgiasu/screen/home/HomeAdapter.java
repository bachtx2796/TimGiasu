package com.ptit.bb.timgiasu.screen.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.ptit.bb.timgiasu.R;
import com.ptit.bb.timgiasu.data.dto.ItemRecruit;

import java.util.List;

import butterknife.ButterKnife;

public class HomeAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<ItemRecruit> mItemRecruits;

    public HomeAdapter(Context mContext, List<ItemRecruit> mItemRecruits) {
        this.mContext = mContext;
        this.mItemRecruits = mItemRecruits;
    }

    public class HomeHolder extends RecyclerView.ViewHolder {

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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HomeHolder homeHolder = (HomeHolder) holder;

    }

    @Override
    public int getItemCount() {
        return mItemRecruits.size();
    }
}
