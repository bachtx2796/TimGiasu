package com.ptit.bb.timgiasu.screen.register;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.support.v4.content.ContextCompat;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gemvietnam.base.viper.ViewFragment;
import com.gemvietnam.utils.StringUtils;
import com.ptit.bb.timgiasu.R;
import com.ptit.bb.timgiasu.Utils.AppUtils;
import com.ptit.bb.timgiasu.Utils.DateTimeUtil;
import com.ptit.bb.timgiasu.customview.Confirmdialog;
import com.ptit.bb.timgiasu.data.dto.UserDTO;
import com.ptit.bb.timgiasu.prewrapper.PrefWrapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    @BindView(R.id.citySpn)
    Spinner mCitiesSp;

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

        initCities();

    }

    private void initCities() {
        ArrayAdapter arrayAdapter = new ArrayAdapter(getViewContext(), R.layout.item_simple_spinner, R.id.text, AppUtils.citiesVN());
        mCitiesSp.setAdapter(arrayAdapter);
    }

    private void initGenders() {
        mGenders = new ArrayList<>();
        mGenders.add("Nam");
        mGenders.add("Nữ");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getViewContext(), R.layout.item_simple_spinner, R.id.text, mGenders);
        mGenderSp.setAdapter(arrayAdapter);
    }

    @OnClick(R.id.backIv)
    public void back() {
        mPresenter.back();
    }

    @OnClick(R.id.signUpTv)
    public void signup() {
        final String fullname = mFullNameEt.getText().toString();
        final String email = mEmailEt.getText().toString();
        final String phoneNo = mPhoneEt.getText().toString();
        final String gender = mGenderSp.getSelectedItem().toString();
        final String pass = mPassEt.getText().toString();
        final String confirmpass = mConfirmPassEt.getText().toString();
        final String dob = mDoBTv.getText().toString();
        final String city = (String) mCitiesSp.getSelectedItem();
        final String address = mAddressTv.getText().toString();

        if (StringUtils.isEmpty(fullname) ||
                StringUtils.isEmpty(email) ||
                StringUtils.isEmpty(phoneNo) ||
                StringUtils.isEmpty(gender) ||
                StringUtils.isEmpty(dob) ||
                StringUtils.isEmpty(pass) ||
                StringUtils.isEmpty(confirmpass)||
                StringUtils.isEmpty(address)) {
            Toast.makeText(getViewContext(), "Nhập đủ thông tin các trường", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isEmailValid(email)) {
            Toast.makeText(getViewContext(), "Email không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!confirmpass.equals(pass)) {
            Toast.makeText(getViewContext(), "Xác nhận mật khẩu không đúng", Toast.LENGTH_SHORT).show();
            return;
        }

        final Confirmdialog confirmdialog = new Confirmdialog(getViewContext(), "Xác nhận", "Chúng tôi sẽ gửi mã đăng kí đến SĐT của bạn");
        confirmdialog.setmOnConfirmListener(new Confirmdialog.OnConfirmListener() {
            @Override
            public void onClickConfirm() {
                mPresenter.signup(fullname, email, phoneNo, gender, pass, confirmpass, dob,city,address);
                confirmdialog.dismiss();
            }
        });
        confirmdialog.show();
    }

    private boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @OnClick(R.id.dateOfBirthTv)
    public void showDatePickerDialog() {
        int day;
        int month;
        int year;

        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                mDateOfBirth.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                mDateOfBirth.set(Calendar.MONTH, monthOfYear);
                mDateOfBirth.set(Calendar.YEAR, year);
                mDoBTv.setTextColor(ContextCompat.getColor(getViewContext(), R.color.text_black));
                mDoBTv.setText(DateTimeUtil.dateToString(mDateOfBirth.getTime()));
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
        pic.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
        pic.setTitle("Chọn ngày");
        pic.show();
    }

    @OnClick(R.id.address_tv)
    public void showMap() {
        mPresenter.showMap();
    }

    @Override
    public void setLocation(String location) {
        mAddressTv.setText(location);
    }

}
