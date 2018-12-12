package com.ptit.bb.timgiasu.screen.filter;

import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.gemvietnam.base.viper.ViewFragment;
import com.ptit.bb.timgiasu.R;
import com.ptit.bb.timgiasu.Utils.AppUtils;
import com.ptit.bb.timgiasu.customview.PickDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The Filter Fragment
 */
public class FilterFragment extends ViewFragment<FilterContract.Presenter> implements FilterContract.View {

    @BindView(R.id.gender_sp)
    Spinner mGenderSp;
    @BindView(R.id.class_tv)
    TextView mClassTv;
    @BindView(R.id.subject_tv)
    TextView mSubjectTv;

    private List<String> mGenders;
    private List<String> mClasses;
    private List<String> mSubjects;

    public static FilterFragment getInstance() {
        return new FilterFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_filter;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        initGenders();

    }

    private void initGenders() {
        mGenders = new ArrayList<>();
        mGenders.add("Nam");
        mGenders.add("Nữ");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getViewContext(), R.layout.item_simple_spinner, R.id.text, mGenders);
        mGenderSp.setAdapter(arrayAdapter);
    }

    @OnClick(R.id.class_tv)
    public void pickClass() {
        PickDialog pickDialog = new PickDialog(getViewContext(), AppUtils.classes(), mClasses);
        pickDialog.setmOnSelectedListener(new PickDialog.OnSelectedListener() {
            @Override
            public void onSelected(List<String> data) {
                mClassTv.setText(data.toString());
                mClasses = data;
            }
        });
        pickDialog.show();
    }

    @OnClick(R.id.subject_tv)
    public void pickSubject() {
        PickDialog pickDialog = new PickDialog(getViewContext(), AppUtils.subjects(), mSubjects);
        pickDialog.setmOnSelectedListener(new PickDialog.OnSelectedListener() {
            @Override
            public void onSelected(List<String> data) {
                mSubjectTv.setText(data.toString());
                mSubjects = data;
            }
        });
        pickDialog.show();
    }

    @OnClick(R.id.back_iv)
    public void back() {
        mPresenter.back();
    }

    @OnClick(R.id.apply_bt)
    public void apply() {
        String gender = mGenderSp.getSelectedItem().toString();
        mPresenter.apply(gender, mClasses, mSubjects);
    }
}
