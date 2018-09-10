package com.ptit.bb.timgiasu.screen.editpost;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.EditText;
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
import com.ptit.bb.timgiasu.data.dto.PostDTO;

import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The EditPost Fragment
 */
public class EditPostFragment extends ViewFragment<EditPostContract.Presenter> implements EditPostContract.View {

    @BindView(R.id.address_tv)
    TextView mAddresTv;
    @BindView(R.id.class_tv)
    TextView mClassTv;
    @BindView(R.id.subject_tv)
    TextView mSubjectTv;
    @BindView(R.id.time_et)
    EditText mTimeEt;
    @BindView(R.id.salary_et)
    EditText mSalaryEt;
    @BindView(R.id.image1)
    SimpleDraweeView mImage1;
    @BindView(R.id.image2)
    SimpleDraweeView mImage2;
    @BindView(R.id.post_bt)
    TextView mPostBt;

    private List<String> mClasses;
    private List<String> mSubjects;
    private List<String> mListUri;
    FirebaseStorage storage;

    private static final int REQUEST_PICK_PICTURE = 100;
    private static final int REQUEST_PICK_PICTURE1 = 101;
    private static final int REQUEST_PICK_PICTURE2 = 102;

    public static EditPostFragment getInstance() {
        return new EditPostFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_post;
    }

    @Override
    public void initLayout() {
        super.initLayout();

        storage = FirebaseStorage.getInstance();
    }

    @Override
    public void bindPost(PostDTO mPost) {
        mClasses = mPost.getClasses();
        mSubjects = mPost.getSubjects();
        mListUri = mPost.getUris();
        mAddresTv.setText(mPost.getAddress());
        mClassTv.setText(mPost.getClasses().toString());
        mSubjectTv.setText(mPost.getSubjects().toString());
        mTimeEt.setText(mPost.getTime());
        mSalaryEt.setText(mPost.getSalary());
        mImage1.setImageURI(mPost.getUris().get(0));
        mImage2.setImageURI(mPost.getUris().get(1));
        mPostBt.setText("Lưu");
    }

    @Override
    public void setLocation(String location) {
        mAddresTv.setText(location);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PICK_PICTURE1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                chooseImageFromSDCard(1);
        }

        if (requestCode == REQUEST_PICK_PICTURE2) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                chooseImageFromSDCard(2);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null || resultCode == Activity.RESULT_CANCELED) {
            return;
        }
        switch (requestCode) {
            case REQUEST_PICK_PICTURE1:
                handlePickImage(data, mImage1);
                break;
            case REQUEST_PICK_PICTURE2:
                handlePickImage(data, mImage2);
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handlePickImage(Intent data, SimpleDraweeView iv) {
        DialogUtils.showProgressDialog(getViewContext());
        uploadImageWithFb(data, iv);
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
                            if (iv.getId() == R.id.image1) {
                                mListUri.set(0, uri.toString());
                            } else if (iv.getId() == R.id.image2) {
                                mListUri.set(1, uri.toString());
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

    private void chooseImageFromSDCard(int pos) {
        Intent pickIntent = new Intent();
        pickIntent.setType("image/*");
        pickIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pickIntent, REQUEST_PICK_PICTURE + pos);
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

    @OnClick(R.id.address_tv)
    public void pickAddress() {
        mPresenter.pickAddress();
    }

    @OnClick(R.id.back_iv)
    public void back() {
        mPresenter.back();
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

    @OnClick(R.id.post_bt)
    public void savePost(){
        String address = mAddresTv.getText().toString();
        String time = mTimeEt.getText().toString();
        String salary = mSalaryEt.getText().toString();
        if (StringUtils.isEmpty(address) || mClasses == null || mSubjects == null || StringUtils.isEmpty(time) || StringUtils.isEmpty(salary)) {
            Toast.makeText(getViewContext(), "Điền đủ thông tin các trường.", Toast.LENGTH_SHORT).show();
            return;
        }
        mPresenter.savePost(address, mClasses, mSubjects, time, salary, mListUri);
    }
}
