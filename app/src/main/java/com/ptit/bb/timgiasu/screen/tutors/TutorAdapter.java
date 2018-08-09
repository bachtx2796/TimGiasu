package com.ptit.bb.timgiasu.screen.tutors;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ptit.bb.timgiasu.R;
import com.ptit.bb.timgiasu.data.dto.Tutor;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TutorAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<Tutor> mTutors;

    public TutorAdapter(Context mContext, List<Tutor> mTutors) {
        this.mContext = mContext;
        this.mTutors = mTutors;
    }

    public class TutorHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name_tv)
        TextView mNamrTv;
        @BindView(R.id.distance_tv)
        TextView mDistanceTv;
        @BindView(R.id.job_tv)
        TextView mJobTv;
        @BindView(R.id.rating_bar)
        RatingBar mRatingBar;
        @BindView(R.id.majoy_tv)
        TextView mMajoyTv;
        @BindView(R.id.class_tv)
        TextView mClassTv;
        @BindView(R.id.save_bt)
        TextView mSaveBt;

        public TutorHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = View.inflate(mContext, R.layout.item_teacher,null);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(layoutParams);
        return new TutorHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TutorHolder tutorHolder = (TutorHolder) holder;
        tutorHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mTutors.size();
    }
}
