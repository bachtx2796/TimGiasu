package com.ptit.bb.timgiasu.customview;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ptit.bb.timgiasu.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Confirmdialog extends Dialog {

    @BindView(R.id.dialog_title_tv)
    TextView mTitleTv;
    @BindView(R.id.dialog_des_tv)
    TextView mContentTv;

    private String mContent;
    private String mTitle;

    public Confirmdialog(@NonNull Context context, String title, String content) {
        super(context);
        mTitle = title;
        mContent = content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.dialog_confirm);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        ButterKnife.bind(this);

        mTitleTv.setText(mTitle);
        mContentTv.setText(mContent);
    }

    @OnClick(R.id.dialog_cancel_tv)
    public void cancel(){
        dismiss();
    }
}
