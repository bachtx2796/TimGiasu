package com.ptit.bb.timgiasu.customview;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RatingBar;

import com.ptit.bb.timgiasu.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RatingDialog extends Dialog {

    @BindView(R.id.rating_bar)
    RatingBar mRatingbar;

    private OnRatingListener mOnRatingListener;
    private int mRate;

    public void setmOnRatingListener(OnRatingListener mOnRatingListener) {
        this.mOnRatingListener = mOnRatingListener;
    }

    public RatingDialog(@NonNull Context context, Integer rate) {
        super(context);
        if (rate != null) {
            mRate = rate;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.dialog_rating);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        ButterKnife.bind(this);

        mRatingbar.setRating(mRate);

    }

    @OnClick(R.id.dialog_cancel_tv)
    public void cancel() {
        dismiss();
    }

    @OnClick(R.id.dialog_ok_tv)
    public void ok() {
        mOnRatingListener.onRating(mRatingbar.getRating());
        dismiss();
    }

    public interface OnRatingListener {
        void onRating(float rate);
    }
}
