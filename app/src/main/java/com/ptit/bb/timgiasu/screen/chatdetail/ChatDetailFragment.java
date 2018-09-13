package com.ptit.bb.timgiasu.screen.chatdetail;

import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.gemvietnam.base.viper.ViewFragment;
import com.gemvietnam.utils.RecyclerUtils;
import com.gemvietnam.utils.StringUtils;
import com.ptit.bb.timgiasu.R;
import com.ptit.bb.timgiasu.data.dto.PostDTO;

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
    public void bindPost(PostDTO mPost) {

    }

    @Override
    public void bindUsername(String name) {
        mUsernameTv.setText(name);
    }

    @Override
    public void bindListMsg(ChatAdapter mAdapter) {
        RecyclerUtils.setupVerticalRecyclerView(getViewContext(),mChatRv);
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
    public void sendMsg(){
        if (!StringUtils.isEmpty(mMessageEt.getText().toString())){
            mPresenter.sendMsg(mMessageEt.getText().toString());
            mMessageEt.setText("");
        }
    }
}
