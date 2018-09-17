package com.ptit.bb.timgiasu.screen.postdetail;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ptit.bb.timgiasu.R;
import com.ptit.bb.timgiasu.screen.home.HomeAdapter;

import java.util.List;

public class ImagePagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<String> mUris;

    private OnItemImageClickListener mOnItemImageClickListener;

    public void setmOnItemImageClickListener(OnItemImageClickListener mOnItemImageClickListener) {
        this.mOnItemImageClickListener = mOnItemImageClickListener;
    }

    public ImagePagerAdapter(Context mContext, List<String> mUris) {
        this.mContext = mContext;
        this.mUris = mUris;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mUris.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.image_layout, container, false);

        SimpleDraweeView imageItem = view.findViewById(R.id.image_item);
        imageItem.setImageURI(mUris.get(position));


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemImageClickListener.onItemClick(mUris, position);
            }
        });

        container.addView(view);

        return view;
    }

    public interface OnItemImageClickListener {
        void onItemClick(List<String> list, int position);
    }
}
