package com.ptit.bb.timgiasu.screen.tutors;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ptit.bb.timgiasu.R;
import com.ptit.bb.timgiasu.data.dto.TutorDTO;
import com.ptit.bb.timgiasu.data.dto.UserDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TutorAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<UserDTO> mTutors;
    private OnItemTutorClickListenr mOnItemTutorClickListenr;

    public void setmOnItemTutorClickListenr(OnItemTutorClickListenr mOnItemTutorClickListenr) {
        this.mOnItemTutorClickListenr = mOnItemTutorClickListenr;
    }

    public TutorAdapter(Context mContext, List<UserDTO> mTutors) {
        this.mContext = mContext;
        this.mTutors = mTutors;
    }

    public class TutorHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.avatar_iv)
        SimpleDraweeView mAvatarIv;
        @BindView(R.id.name_tv)
        TextView mNameTv;
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
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = View.inflate(mContext, R.layout.item_teacher, null);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(layoutParams);
        return new TutorHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        TutorHolder tutorHolder = (TutorHolder) holder;
        tutorHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemTutorClickListenr.onTutorClick(position);
            }
        });
        UserDTO user = mTutors.get(position);
        tutorHolder.mAvatarIv.setImageURI(user.getAvatar());
        tutorHolder.mNameTv.setText(user.getName());
        tutorHolder.mJobTv.setText(user.getAddress());
        tutorHolder.mRatingBar.setRating(getRating(user.getRatings()));
        tutorHolder.mMajoyTv.setText("Chuyên môn: " + user.getSubjects().toString());
        tutorHolder.mClassTv.setText("Lớp: " + user.getClasses().toString());

    }

    private float getRating(HashMap<String, Integer> ratings) {
        HashMap<String, Integer> tmp = ratings;
        int sum = 0;
        for (Map.Entry<String, Integer> entry : tmp.entrySet()) {
            sum += entry.getValue();
        }
        return sum / tmp.size();
    }

    @Override
    public int getItemCount() {
        return mTutors.size();
    }

    public interface OnItemTutorClickListenr {
        void onTutorClick(int position);
    }
}
