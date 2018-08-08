package com.example.bb.timgiasu.screen.chat;

import android.support.v7.widget.RecyclerView;

import com.example.bb.timgiasu.Utils.VerticalSpaceItemDecoration;
import com.gemvietnam.base.viper.ViewFragment;
import com.example.bb.timgiasu.R;
import com.gemvietnam.utils.RecyclerUtils;

import butterknife.BindView;

/**
 * The Chat Fragment
 */
public class ChatFragment extends ViewFragment<ChatContract.Presenter> implements ChatContract.View {

    @BindView(R.id.chat_rv)
    RecyclerView mChatRv;

    public static ChatFragment getInstance() {
        return new ChatFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chat;
    }

    @Override
    public void bindView(ChatAdapter mChatAdapter) {
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.margin_5dp);
        mChatRv.addItemDecoration(new VerticalSpaceItemDecoration(spacingInPixels));
        RecyclerUtils.setupVerticalRecyclerView(getViewContext(), mChatRv);
        mChatRv.setAdapter(mChatAdapter);
    }
}
