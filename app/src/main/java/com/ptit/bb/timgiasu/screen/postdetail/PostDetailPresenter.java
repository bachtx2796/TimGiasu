package com.ptit.bb.timgiasu.screen.postdetail;

import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;
import com.ptit.bb.timgiasu.data.dto.PostDTO;
import com.ptit.bb.timgiasu.screen.editpost.EditPostPresenter;

/**
 * The PostDetail Presenter
 */
public class PostDetailPresenter extends Presenter<PostDetailContract.View, PostDetailContract.Interactor>
        implements PostDetailContract.Presenter {

    private PostDTO mPost;

    public PostDetailPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public PostDetailContract.View onCreateView() {
        return PostDetailFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
        mView.bindView(mPost);
    }

    @Override
    public PostDetailContract.Interactor onCreateInteractor() {
        return new PostDetailInteractor(this);
    }

    public PostDetailPresenter setPost(PostDTO postDTO) {
        mPost = postDTO;
        return this;
    }

    @Override
    public void editPost() {
        new EditPostPresenter(mContainerView)
                .setPost(mPost)
                .pushView();
    }
}
