package com.ptit.bb.timgiasu.screen.viewimage;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.gemvietnam.utils.image.ImageUtils;
import com.ptit.bb.timgiasu.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends PagerAdapter {

    private Context mContext;
    private List<String> listImage;

    public ImageAdapter(Context mContext, List<String> listImage) {
        this.mContext = mContext;
        this.listImage = listImage;
    }

    @Override
    public int getCount() {
        return listImage.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View imageLayout = LayoutInflater.from(mContext).inflate(R.layout.item_image, container, false);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageLayout.setLayoutParams(params);
        ImageView imageIv = imageLayout.findViewById(R.id.image_iv);

        Picasso.with(container.getContext()).load(Uri.parse(listImage.get(position))).into(imageIv);
        container.addView(imageLayout);

        return imageLayout;
    }
}
