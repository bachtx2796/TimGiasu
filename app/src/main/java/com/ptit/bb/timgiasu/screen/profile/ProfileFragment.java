package com.ptit.bb.timgiasu.screen.profile;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.common.file.FileUtils;
import com.gemvietnam.base.viper.ViewFragment;
import com.gemvietnam.utils.DialogUtils;
import com.gemvietnam.utils.PermissionUtils;
import com.ptit.bb.timgiasu.R;
import com.ptit.bb.timgiasu.Utils.AppUtils;
import com.ptit.bb.timgiasu.Utils.FileUtil;
import com.ptit.bb.timgiasu.data.ImgurServiceBuilder;
import com.ptit.bb.timgiasu.data.dto.ImgurBaseData;
import com.ptit.bb.timgiasu.data.dto.UserDTO;
import com.ptit.bb.timgiasu.prewrapper.PrefWrapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The Profile Fragment
 */
public class ProfileFragment extends ViewFragment<ProfileContract.Presenter> implements ProfileContract.View {

    @BindView(R.id.fullNameEdt)
    EditText mFullnameEt;
    @BindView(R.id.emailEdt)
    EditText mEmailEt;
    @BindView(R.id.mobileEdt)
    EditText mMobileEt;
    @BindView(R.id.genderProfileSpn)
    Spinner mGenderSp;
    @BindView(R.id.cityProfileSpn)
    Spinner mCitySp;
    @BindView(R.id.dateOBTv)
    TextView mDobTv;
    @BindView(R.id.addressTv)
    TextView mAddressTv;
    @BindView(R.id.avatarIv)
    ImageView mAvatarIv;
    @BindView(R.id.profileEditTv)
    TextView mEditBt;
    @BindView(R.id.profileSaveTv)
    TextView mSaveBt;

    private static final int REQUEST_PICK_PICTURE = 100;
    private String mLinkAvatar = "";
    private UserDTO mUser;

    public static ProfileFragment getInstance() {
        return new ProfileFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_profile;
    }

    @OnClick(R.id.logoutTv)
    public void logout() {
        mPresenter.logout();
    }

    @Override
    public void initLayout() {
        super.initLayout();

        initGenders();

        initCities();

        fillDataUser();
    }

    private void fillDataUser() {
        mUser = PrefWrapper.getUser(getViewContext());
        if (mUser.getAvatar() != null) {
            mAvatarIv.setImageURI(Uri.parse(mUser.getAvatar()));
        }
        mFullnameEt.setText(mUser.getName());
        mEmailEt.setText(mUser.getEmail());
        mMobileEt.setText("0" + mUser.getPhoneNo());
        mGenderSp.setSelection(mUser.getGender().equals("Nam") ? 0 : 1);
        for (int i = 0; i < 64; i++) {
            String city = AppUtils.citiesVN()[i];
            if (city.equals(mUser.getCity())) {
                mCitySp.setSelection(i);
                break;
            }
        }
        mDobTv.setText(mUser.getDob());
        mAddressTv.setText(mUser.getAddress());
    }

    private void initGenders() {
        String[] mGenders = {"Nam", "Nữ"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(getViewContext(), R.layout.item_simple_spinner, R.id.text, mGenders);
        mGenderSp.setAdapter(arrayAdapter);
        mGenderSp.setEnabled(false);
    }

    private void initCities() {
        ArrayAdapter arrayAdapter = new ArrayAdapter(getViewContext(), R.layout.item_simple_spinner, R.id.text, AppUtils.citiesVN());
        mCitySp.setAdapter(arrayAdapter);
        mCitySp.setEnabled(false);
    }

    @OnClick(R.id.profileEditTv)
    public void editProfile() {
        isEnableEdit(true);
    }

    @OnClick(R.id.profileSaveTv)
    public void saveProfile() {
        isEnableEdit(false);
        mUser.setAvatar(mLinkAvatar);
        mUser.setAddress(mAddressTv.getText().toString());
        mUser.setCity(mCitySp.getSelectedItem().toString());
        mUser.setDob(mDobTv.getText().toString());
        mUser.setEmail(mEmailEt.getText().toString());
        mUser.setGender(mGenderSp.getSelectedItem().toString());
        mUser.setName(mFullnameEt.getText().toString());
        mUser.setPhoneNo(mMobileEt.getText().toString());
        mPresenter.saveUser(mUser);
    }

    private void isEnableEdit(boolean isEnable) {
        if (isEnable) {
            mFullnameEt.setInputType(InputType.TYPE_CLASS_TEXT);
            mEmailEt.setInputType(InputType.TYPE_CLASS_TEXT);
            mMobileEt.setInputType(InputType.TYPE_CLASS_TEXT);
            mCitySp.setEnabled(true);
            mGenderSp.setEnabled(true);
            mDobTv.setEnabled(true);
            mAddressTv.setEnabled(true);
            mAvatarIv.setEnabled(true);
            mEditBt.setVisibility(View.GONE);
            mSaveBt.setVisibility(View.VISIBLE);
        } else {
            mFullnameEt.setInputType(InputType.TYPE_NULL);
            mEmailEt.setInputType(InputType.TYPE_NULL);
            mMobileEt.setInputType(InputType.TYPE_NULL);
            mCitySp.setEnabled(false);
            mGenderSp.setEnabled(false);
            mDobTv.setEnabled(false);
            mAddressTv.setEnabled(false);
            mAvatarIv.setEnabled(false);
            mSaveBt.setVisibility(View.GONE);
            mEditBt.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.avatarIv)
    public void pickImage() {
        if (!PermissionUtils.needRequestPermissions(getViewContext(), this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PICK_PICTURE)) {
            chooseAvatarFromSDCard();
        }
    }

    private void chooseAvatarFromSDCard() {
        Intent pickIntent = new Intent();
        pickIntent.setType("image/*");
        pickIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pickIntent, REQUEST_PICK_PICTURE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PICK_PICTURE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                chooseAvatarFromSDCard();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null || resultCode == Activity.RESULT_CANCELED) {
            return;
        }
        switch (requestCode) {
            case REQUEST_PICK_PICTURE:
                handlePickImage(data);
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handlePickImage(Intent data) {
        DialogUtils.showProgressDialog(getViewContext());
        try {
            Bitmap bm = MediaStore.Images.Media.getBitmap(getViewContext().getContentResolver(), data.getData());
            uploadImageFromBitmap(bm);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void uploadImageFromBitmap(final Bitmap photo) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] arr = byteArrayOutputStream.toByteArray();
        String imgBase64 = Base64.encodeToString(arr, Base64.DEFAULT);
        ImgurServiceBuilder.getImgurAPI().pushImageToImgur(imgBase64).enqueue(new Callback<ImgurBaseData>() {
            @Override
            public void onResponse(Call<ImgurBaseData> call, Response<ImgurBaseData> response) {
                DialogUtils.dismissProgressDialog();
                if (response.isSuccessful()) {
                    mLinkAvatar = response.body().getImg().getLink();
                    mAvatarIv.setImageURI(Uri.parse(mLinkAvatar));
                } else {
                    Toast.makeText(getViewContext(), "upload lỗi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ImgurBaseData> call, Throwable t) {
                DialogUtils.dismissProgressDialog();
                Toast.makeText(getViewContext(), "lỗi", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
