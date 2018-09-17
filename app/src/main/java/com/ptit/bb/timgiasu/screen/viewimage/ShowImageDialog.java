package com.ptit.bb.timgiasu.screen.viewimage;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.view.WindowManager;

import com.ptit.bb.timgiasu.R;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShowImageDialog extends Dialog {

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.circle_page_indicator)
    CirclePageIndicator circlePageIndicator;

    private List<String> listImage;
    private int mPosImage = 0;

    public ShowImageDialog(@NonNull Context context, int theme, List<String> listImage, int mPosImage) {
        super(context,theme);
        this.listImage = listImage;
        this.mPosImage = mPosImage;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_show_image);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        ButterKnife.bind(this);
        setCancelable(true);
        initView();
    }

    private void initView() {
        viewPager.setAdapter(new ImageAdapter(getContext(), listImage));
        viewPager.setCurrentItem(mPosImage);
        circlePageIndicator.setViewPager(viewPager);
    }

    @OnClick(R.id.cancel_bt)
    public void cancel(){
        dismiss();
    }
}
