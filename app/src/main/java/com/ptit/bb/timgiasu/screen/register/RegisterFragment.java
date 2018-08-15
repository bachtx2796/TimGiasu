package com.ptit.bb.timgiasu.screen.register;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.support.v4.content.ContextCompat;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.gemvietnam.base.viper.ViewFragment;
import com.gemvietnam.utils.StringUtils;
import com.ptit.bb.timgiasu.R;
import com.ptit.bb.timgiasu.screen.map.MapPresenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The Register Fragment
 */
public class RegisterFragment extends ViewFragment<RegisterContract.Presenter> implements RegisterContract.View {

    @BindView(R.id.fullNameEt)
    EditText mFullNameEt;
    @BindView(R.id.emailEt)
    EditText mEmailEt;
    @BindView(R.id.phoneEt)
    EditText mPhoneEt;
    @BindView(R.id.genderSpn)
    Spinner mGenderSp;
    @BindView(R.id.dateOfBirthTv)
    TextView mDoBTv;
    @BindView(R.id.address_tv)
    TextView mAddressTv;
    @BindView(R.id.passwordEt)
    EditText mPassEt;
    @BindView(R.id.confirmPasswordEt)
    EditText mConfirmPassEt;

    private List<String> mGenders;
    private Calendar mDateOfBirth = Calendar.getInstance();

    public static RegisterFragment getInstance() {
        return new RegisterFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
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
        ArrayAdapter arrayAdapter = new ArrayAdapter(getViewContext(),R.layout.item_simple_spinner,R.id.text,mGenders);
        mGenderSp.setAdapter(arrayAdapter);
    }

    @OnClick(R.id.backIv)
    public void back(){
        mPresenter.back();
    }

    @OnClick(R.id.signUpTv)
    public void signup(){
        String fullname = mFullNameEt.getText().toString();
        String email = mEmailEt.getText().toString();
        String phoneNo = mPhoneEt.getText().toString();
        String gender = mGenderSp.getSelectedItem().toString();
        String pass = mPassEt.getText().toString();
        String confirmpass = mConfirmPassEt.getText().toString();

        mPresenter.signup();
    }

    @OnClick(R.id.dateOfBirthTv)
    public void showDatePickerDialog(){
        int day;
        int month;
        int year;

        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                mDateOfBirth.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                mDateOfBirth.set(Calendar.MONTH, monthOfYear);
                mDateOfBirth.set(Calendar.YEAR, year);
                //appUserRegisterDTO.date_of_birth = mDateOfBirth.getServerDateTimeFormat();
                mDoBTv.setTextColor(ContextCompat.getColor(getViewContext(), R.color.text_black));
                mDoBTv.setText(mDateOfBirth.getTime().toString());
            }
        };

        Calendar c = Calendar.getInstance();
        if (!StringUtils.isEmpty(mDoBTv.getText().toString())) {
            year = mDateOfBirth.get(Calendar.YEAR);
            month = mDateOfBirth.get(Calendar.MONTH);
            day = mDateOfBirth.get(Calendar.DAY_OF_MONTH);
        } else {

            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
        }

       DatePickerDialog pic = new DatePickerDialog(
                getViewContext(),
                AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,
                callback, year, month, day);
//        pic.datePicker.minDate = System.currentTimeMillis() - 1000
        pic.setTitle("Chọn ngày");
        pic.show();
    }

    @OnClick(R.id.address_tv)
    public void showMap(){
        mPresenter.showMap();
    }
}
