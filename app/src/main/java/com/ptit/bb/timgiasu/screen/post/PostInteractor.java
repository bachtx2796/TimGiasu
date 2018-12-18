package com.ptit.bb.timgiasu.screen.post;

import com.gemvietnam.base.viper.Interactor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.FirebaseDatabase;
import com.ptit.bb.timgiasu.Utils.DBConstan;
import com.ptit.bb.timgiasu.data.dto.PostDTO;

import java.util.List;

/**
 * The Post interactor
 */
class PostInteractor extends Interactor<PostContract.Presenter>
        implements PostContract.Interactor {

    PostInteractor(PostContract.Presenter presenter) {
        super(presenter);
    }


    @Override
    public void newPost(String city, String idPost, PostDTO postDTO, OnCompleteListener<Void> onCompleteListener) {
        FirebaseDatabase.getInstance().getReference(DBConstan.CITIES).child(city).child(DBConstan.POSTS).child(idPost).setValue(postDTO).addOnCompleteListener(onCompleteListener);
    }
}
