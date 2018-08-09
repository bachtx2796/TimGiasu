package com.ptit.bb.timgiasu.screen.chatdetail;

import com.gemvietnam.base.viper.ViewFragment;
import com.ptit.bb.timgiasu.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The ChatDetail Fragment
 */
public class ChatDetailFragment extends ViewFragment<ChatDetailContract.Presenter> implements ChatDetailContract.View {

    public static ChatDetailFragment getInstance() {
        return new ChatDetailFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chat_detail;
    }

    @OnClick(R.id.backIv)
    public void back(){
        mPresenter.back();
    }
}
