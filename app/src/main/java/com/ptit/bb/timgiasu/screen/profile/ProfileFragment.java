package com.ptit.bb.timgiasu.screen.profile;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.gemvietnam.base.viper.ViewFragment;
import com.gemvietnam.utils.DialogUtils;
import com.gemvietnam.utils.PermissionUtils;
import com.gemvietnam.utils.StringUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ptit.bb.timgiasu.R;
import com.ptit.bb.timgiasu.Utils.AppUtils;
import com.ptit.bb.timgiasu.customview.PickDialog;
import com.ptit.bb.timgiasu.data.ImgurServiceBuilder;
import com.ptit.bb.timgiasu.data.dto.ImgurBaseData;
import com.ptit.bb.timgiasu.data.dto.UserDTO;
import com.ptit.bb.timgiasu.prewrapper.PrefWrapper;
import com.ptit.bb.timgiasu.screen.history.HistoryPresenter;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    SimpleDraweeView mAvatarIv;
    @BindView(R.id.profileEditTv)
    TextView mEditBt;
    @BindView(R.id.profileSaveTv)
    TextView mSaveBt;
    @BindView(R.id.info_tutor_ll)
    LinearLayout mInfoTutorLL;
    @BindView(R.id.registerTutorBt)
    TextView mRegisterTutorBt;
    @BindView(R.id.image1)
    SimpleDraweeView mImage1;
    @BindView(R.id.image2)
    SimpleDraweeView mImage2;
    @BindView(R.id.image3)
    SimpleDraweeView mImage3;
    @BindView(R.id.classTv)
    TextView mClassTv;
    @BindView(R.id.subjectTv)
    TextView mSubjectTv;
    @BindView(R.id.timeTv)
    EditText mTimeEt;
    @BindView(R.id.serviceFeeTv)
    EditText mServiceFeeEt;

    private static final int REQUEST_PICK_PICTURE = 100;
    private static final int REQUEST_PICK_PICTURE1 = 101;
    private static final int REQUEST_PICK_PICTURE2 = 102;
    private static final int REQUEST_PICK_PICTURE3 = 103;
    private String mLinkAvatar = "";
    private UserDTO mUser;

    FirebaseStorage storage;

    private List<String> mListUri;
    private List<String> mClasses;
    private List<String> mSubjects;

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

        storage = FirebaseStorage.getInstance();

        initGenders();

        initCities();

        fillDataUser();

    }

    private void fillDataUser() {
        mUser = PrefWrapper.getUser(getViewContext());
        mAvatarIv.setEnabled(false);
        if (mUser.getAvatar() != null) {
            mAvatarIv.setImageURI(Uri.parse(mUser.getAvatar()));
        }
        mFullnameEt.setText(mUser.getName());
        mEmailEt.setText(mUser.getEmail());
        if (mUser.getPhoneNo().charAt(0) == '0') {
            mMobileEt.setText(mUser.getPhoneNo());
        } else {
            mMobileEt.setText("0" + mUser.getPhoneNo());
        }
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

        if (mUser.isTutor()) {
            mRegisterTutorBt.setText("Thông tin gia sư");
            mInfoTutorLL.setVisibility(View.VISIBLE);
        } else {
            mRegisterTutorBt.setText("Đăng kí thông tin gia sư");
            mInfoTutorLL.setVisibility(View.GONE);
        }

        mListUri = new ArrayList<>();
        mListUri.add("");
        mListUri.add("");
        mListUri.add("");

        if (mUser.isTutor()) {
            mRegisterTutorBt.setText("Thông tin gia sư");
            mClasses = mUser.getClasses();
            mClassTv.setText(mClasses.toString());
            mSubjects = mUser.getSubjects();
            mSubjectTv.setText(mSubjects.toString());
            mTimeEt.setText(mUser.getTime());
            mServiceFeeEt.setText(mUser.getSalary() + "");
            mListUri = mUser.getUris();
            mImage1.setImageURI(mListUri.get(0));
            mImage2.setImageURI(mListUri.get(1));
            mImage3.setImageURI(mListUri.get(2));
        }
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
        mUser = PrefWrapper.getUser(getViewContext());
        boolean setCity = false;
        boolean setEmail = false;
        isEnableEdit(false);
        mUser.setAvatar(mLinkAvatar);
        mUser.setAddress(mAddressTv.getText().toString());
        if (!mUser.getCity().equals(mCitySp.getSelectedItem().toString())) {
            setCity = true;
            mUser.setCity(mCitySp.getSelectedItem().toString());
        }
        mUser.setDob(mDobTv.getText().toString());
        if (!mUser.getEmail().equals(mEmailEt.getText().toString())) {
            setEmail = true;
            mUser.setEmail(mEmailEt.getText().toString());
        }
        mUser.setGender(mGenderSp.getSelectedItem().toString());
        mUser.setName(mFullnameEt.getText().toString());
        mUser.setPhoneNo(mMobileEt.getText().toString());
        mPresenter.saveUser(mUser, setEmail, setCity);
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

    private void chooseImageFromSDCard(int pos) {
        Intent pickIntent = new Intent();
        pickIntent.setType("image/*");
        pickIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pickIntent, REQUEST_PICK_PICTURE + pos);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PICK_PICTURE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                chooseAvatarFromSDCard();
        }

        if (requestCode == REQUEST_PICK_PICTURE1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                chooseImageFromSDCard(1);
        }

        if (requestCode == REQUEST_PICK_PICTURE2) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                chooseImageFromSDCard(2);
        }

        if (requestCode == REQUEST_PICK_PICTURE3) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                chooseImageFromSDCard(3);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null || resultCode == Activity.RESULT_CANCELED) {
            return;
        }
        switch (requestCode) {
            case REQUEST_PICK_PICTURE:
                handlePickImage(data, mAvatarIv);
                break;
            case REQUEST_PICK_PICTURE1:
                handlePickImage(data, mImage1);
                break;
            case REQUEST_PICK_PICTURE2:
                handlePickImage(data, mImage2);
                break;
            case REQUEST_PICK_PICTURE3:
                handlePickImage(data, mImage3);
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handlePickImage(Intent data, SimpleDraweeView iv) {
        DialogUtils.showProgressDialog(getViewContext());
//        try {
//            Bitmap bm = MediaStore.Images.Media.getBitmap(getViewContext().getContentResolver(), data.getData());
//            uploadImageFromBitmap(bm, iv);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        uploadImageWithFb(data, iv);
    }


    private void uploadImageFromBitmap(final Bitmap photo, final ImageView iv) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] arr = byteArrayOutputStream.toByteArray();
        String imgBase64 = Base64.encodeToString(arr, Base64.DEFAULT);
        ImgurServiceBuilder.getImgurAPI().pushImageToImgur(imgBase64).enqueue(new Callback<ImgurBaseData>() {
            @Override
            public void onResponse(Call<ImgurBaseData> call, Response<ImgurBaseData> response) {
                DialogUtils.dismissProgressDialog();
                if (response.isSuccessful()) {
                    if (iv.getId() == R.id.avatarIv) {
                        mLinkAvatar = response.body().getImg().getLink();
                        iv.setImageURI(Uri.parse(mLinkAvatar));
                    }
                    if (iv.getId() == R.id.image1) {
                        mListUri.set(0, response.body().getImg().getLink());
                        mImage1.setImageURI(response.body().getImg().getLink());
                    }
                    if (iv.getId() == R.id.image2) {
                        mListUri.set(1, response.body().getImg().getLink());
                        mImage2.setImageURI(response.body().getImg().getLink());
                    }
                    if (iv.getId() == R.id.image3) {
                        mListUri.set(2, response.body().getImg().getLink());
                        mImage3.setImageURI(response.body().getImg().getLink());
                    }
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

    private void uploadImageWithFb(Intent data, final SimpleDraweeView iv) {

        Uri filePath = data.getData();
        if (filePath != null) {
            DialogUtils.showProgressDialog(getViewContext());

            StorageReference ref = storage.getReference().child("images/" + UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            DialogUtils.dismissProgressDialog();
                            Uri uri = taskSnapshot.getDownloadUrl();
                            iv.setImageURI(uri);
                            if (iv.getId() == R.id.avatarIv) {
                                mLinkAvatar = uri.toString();
                            } else if (iv.getId() == R.id.image1) {
                                mListUri.set(0, uri.toString());
                            } else if (iv.getId() == R.id.image2) {
                                mListUri.set(1, uri.toString());
                            } else {
                                mListUri.set(2, uri.toString());
                            }

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            DialogUtils.dismissProgressDialog();
                            Toast.makeText(getViewContext(), "Upload lỗi", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }


    @OnClick(R.id.changePasswordTv)
    public void changePassword() {
        mPresenter.changePassword();
    }

    @OnClick(R.id.image1)
    public void selectImage1() {
        if (!PermissionUtils.needRequestPermissions(getViewContext(), this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PICK_PICTURE1)) {
            chooseImageFromSDCard(1);
        }
    }

    @OnClick(R.id.image2)
    public void selectImage2() {
        if (!PermissionUtils.needRequestPermissions(getViewContext(), this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PICK_PICTURE2)) {
            chooseImageFromSDCard(2);
        }
    }

    @OnClick(R.id.image3)
    public void selectImage3() {
        if (!PermissionUtils.needRequestPermissions(getViewContext(), this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PICK_PICTURE3)) {
            chooseImageFromSDCard(3);
        }
    }

    @OnClick(R.id.registerTutorBt)
    public void registerInfoTutor() {
        mInfoTutorLL.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.classTv)
    public void pickClass() {
        PickDialog pickDialog = new PickDialog(getViewContext(), AppUtils.classes());
        pickDialog.setmOnSelectedListener(new PickDialog.OnSelectedListener() {
            @Override
            public void onSelected(List<String> data) {
                mClassTv.setText(data.toString());
                mClasses = data;
            }
        });
        pickDialog.show();
    }

    @OnClick(R.id.subjectTv)
    public void pickSubject() {
        PickDialog pickDialog = new PickDialog(getViewContext(), AppUtils.subjects());
        pickDialog.setmOnSelectedListener(new PickDialog.OnSelectedListener() {
            @Override
            public void onSelected(List<String> data) {
                mSubjectTv.setText(data.toString());
                mSubjects = data;
            }
        });
        pickDialog.show();
    }

    @OnClick(R.id.saveInfoTutorBt)
    public void saveInfoTutorBt() {
        String time = mTimeEt.getText().toString();
        String salary = mServiceFeeEt.getText().toString();
        if (mClasses == null || mSubjects == null || StringUtils.isEmpty(time) || StringUtils.isEmpty(salary)) {
            Toast.makeText(getViewContext(), "Nhập đủ thông tin các trường", Toast.LENGTH_SHORT).show();
            return;
        }
        mPresenter.saveTutor(mClasses, mSubjects, time, salary, mListUri);
    }

    @OnClick(R.id.profile_history_tv)
    public void viewHistory() {
        mPresenter.displayHistory();
    }
}
