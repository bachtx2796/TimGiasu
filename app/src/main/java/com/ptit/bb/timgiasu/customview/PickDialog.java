package com.ptit.bb.timgiasu.customview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ptit.bb.timgiasu.R;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PickDialog extends Dialog {

    @BindView(R.id.data_lv)
    ListView mDataLv;

    private String[] mData;
    private List<String> selecteds;

    private OnSelectedListener mOnSelectedListener;

    public void setmOnSelectedListener(OnSelectedListener mOnSelectedListener) {
        this.mOnSelectedListener = mOnSelectedListener;
    }

    public PickDialog(@NonNull Context context, String[] data) {
        super(context);
        mData = data;
    }

    public PickDialog(@NonNull Context context, String[] data, List<String> selecteds) {
        super(context);
        mData = data;
        this.selecteds = selecteds;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(true);
        setContentView(R.layout.dialog_pick_value);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_multiple_choice, mData);
        mDataLv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        List<String> tmp;
        tmp = Arrays.asList(mData);
        mDataLv.setAdapter(arrayAdapter);
        for (String selected : selecteds) {
            int index = tmp.indexOf(selected);
            mDataLv.setItemChecked(index, true);
        }
        arrayAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.select_bt)
    public void selectValue() {
        int len = mDataLv.getCount();
        List<String> tmp = new ArrayList<>();
        SparseBooleanArray checked = mDataLv.getCheckedItemPositions();
        for (int i = 0; i < len; i++) {
            if (checked.get(i)) {
                tmp.add(mData[i]);
                /* do whatever you want with the checked item */
            }
        }
        mOnSelectedListener.onSelected(tmp);
        dismiss();
    }

    public interface OnSelectedListener {
        void onSelected(List<String> data);
    }


}
