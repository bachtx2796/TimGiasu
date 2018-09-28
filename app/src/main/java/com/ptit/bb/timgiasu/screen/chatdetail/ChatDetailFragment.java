package com.ptit.bb.timgiasu.screen.chatdetail;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.gemvietnam.base.viper.ViewFragment;
import com.gemvietnam.utils.PermissionUtils;
import com.gemvietnam.utils.RecyclerUtils;
import com.gemvietnam.utils.StringUtils;
import com.ptit.bb.timgiasu.R;
import com.ptit.bb.timgiasu.data.dto.PostDTO;
import com.ptit.bb.timgiasu.prewrapper.PrefWrapper;
import com.ptit.bb.timgiasu.screen.profile.ProfilePresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The ChatDetail Fragment
 */
public class ChatDetailFragment extends ViewFragment<ChatDetailContract.Presenter> implements ChatDetailContract.View {

    @BindView(R.id.username_tv)
    TextView mUsernameTv;
    @BindView(R.id.message_et)
    EditText mMessageEt;
    @BindView(R.id.chat_detail_rv)
    RecyclerView mChatRv;
    @BindView(R.id.item_image_iv)
    SimpleDraweeView mItemImageIv;
    @BindView(R.id.item_title_tv)
    TextView mTitleTv;
    @BindView(R.id.item_price_tv)
    TextView mPriceTv;
    @BindView(R.id.accept_tv)
    TextView mAceptBt;
    @BindView(R.id.decline_tv)
    TextView mDeclineBt;

    private static final int REQUEST_PICK_PICTURE = 100;
    private static final int CALL_PHONE = 101;

    public static ChatDetailFragment getInstance() {
        return new ChatDetailFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chat_detail;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        mMessageEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    sendMsg();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void bindPost(PostDTO mPost, String action) {
        if (mPost.getUris() != null) {
            mItemImageIv.setImageURI(mPost.getUris().get(0));
        }
        mTitleTv.setText("Lớp: " + mPost.getClasses().toString() + " / " + mPost.getSubjects() + "\nThời gian: " + mPost.getTime());
        mPriceTv.setText(mPost.getSalary());
        if (mPost.getIdUser().equals(PrefWrapper.getUser(getViewContext()).getId())) {
            if (action.equals("pending")){
                mAceptBt.setVisibility(View.VISIBLE);
                mDeclineBt.setVisibility(View.VISIBLE);
            } else {
                mAceptBt.setVisibility(View.GONE);
                mDeclineBt.setVisibility(View.GONE);
            }
        } else {
            mAceptBt.setText("Liên hệ");
            mAceptBt.setVisibility(View.VISIBLE);
            mDeclineBt.setVisibility(View.GONE);
        }
    }

    @Override
    public void bindUsername(String name) {
        mUsernameTv.setText(name);
    }

    @Override
    public void bindListMsg(ChatAdapter mAdapter) {
        RecyclerUtils.setupVerticalRecyclerView(getViewContext(), mChatRv);
        mChatRv.setAdapter(mAdapter);
    }

    @Override
    public void updateListMsg(int size) {
        mChatRv.scrollToPosition(size - 1);
    }

    @OnClick(R.id.back_iv)
    public void back() {
        mPresenter.back();
    }

    @OnClick(R.id.send_message_iv)
    public void sendMsg() {
        if (!StringUtils.isEmpty(mMessageEt.getText().toString())) {
            mPresenter.sendMsg(mMessageEt.getText().toString());
            mMessageEt.setText("");
        }
    }

    @OnClick(R.id.send_file_iv)
    public void selectImg() {
        if (!PermissionUtils.needRequestPermissions(getViewContext(), this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PICK_PICTURE)) {
            chooseImageFromSDCard();
        }
    }

    @OnClick(R.id.message_heade_ll)
    public void viewPost() {
        mPresenter.viewPost();
    }

    private void chooseImageFromSDCard() {
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
                chooseImageFromSDCard();
        }

        if (requestCode == CALL_PHONE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                mPresenter.callOwner();
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
        mPresenter.uploadImageWithFb(data);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.removeListener();
    }

    @OnClick(R.id.accept_tv)
    public void action() {
        switch (mAceptBt.getText().toString()) {
            case "Liên hệ":
                if (!PermissionUtils.needRequestPermissions(getViewContext(), this, new String[]{Manifest.permission.CALL_PHONE}, CALL_PHONE)) {
                    mPresenter.callOwner();
                }
                break;
            case "Chấp nhận":
                mPresenter.pushNotiAcepted();
                break;
        }
    }

    @OnClick(R.id.decline_tv)
    public void decline() {
        mPresenter.pushNotiDecline();
    }

}
